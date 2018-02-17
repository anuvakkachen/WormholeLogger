package wormholelogger.log

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import wormholelogger.common.WhLogger
import wormholelogger.common.WhLoggerConstants
import wormholelogger.common.WhLogger_Proxy
import wormholelogger.log.internal.Logger
import wormholelogger.log.internal.WhLoggerRouter

/**
 * Log wrapper for [android.util.Log].
 * This will send the formatAndLog to remote wormhole (firebase) if a wormhole portal is installed, if not it logs
 * to the standard [android.util.Log]
 *
 * Call [Log.init] to initialize the logger with the client packageName
 */
class Log {

    companion object {

        private var context: Context? = null
        private var packageName: String? = null
        private val serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                logger = Logger()
                isConnected = false
            }

            override fun onServiceConnected(p0: ComponentName?, p1: IBinder) {
                val loggerProxy: WhLogger = WhLogger_Proxy(p1)
                android.util.Log.v("WhLog", "Connected to WhLogService")
                if (loggerProxy.register(packageName)) {
                    logger = WhLoggerRouter(loggerProxy, packageName!!)
                    isConnected = true
                    android.util.Log.v("WhLog", "Registered to WhLogService")
                } else {
                    android.util.Log.v("WhLog", "Failed to register to WhLogService")
                    context?.unbindService(this)
                }
            }
        }

        /**
         * If true, then the Logger is connected to remote service and logs
         * are being transferred through the wormhole
         */
        @JvmStatic
        var isConnected = false
            private set
        private var logger: Logger = Logger()

        /**
         * Initialize the WormholeLogger [Log] client
         *
         * @param context [Context] of client app
         * @see [Log.destroy]
         */
        internal fun init(context: Context) {
            this.context = context
            packageName = context.packageName
            val intent = Intent(WhLoggerConstants.WH_LOGGER_INTENT)
            val serviceList = context.packageManager.queryIntentServices(intent, 0)
            if (serviceList != null && serviceList.isNotEmpty()) {
                val serviceInfo = serviceList.first()
                if (serviceInfo.serviceInfo != null) {
                    val sIntent = Intent().setClassName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name)
                    android.util.Log.v("WhLog", "Found WhLogService $sIntent")
                    context.bindService(sIntent, serviceConnection, Context.BIND_AUTO_CREATE)
                }
            }
        }

        /**
         * Called during onDestroy to clean up any
         */
        internal fun destroy() {
            if (isConnected) {
                context?.unbindService(serviceConnection)
                isConnected = false
                context = null
                logger = Logger()
            }
        }


        @JvmStatic
        fun v(tag: String, msg: String) = logger.v(tag, msg)

        @JvmStatic
        fun v(tag: String, msg: String, tr: Throwable) = logger.v(tag, msg, tr)

        @JvmStatic
        fun d(tag: String, msg: String) = logger.d(tag, msg)

        @JvmStatic
        fun d(tag: String, msg: String, tr: Throwable) = logger.d(tag, msg, tr)

        @JvmStatic
        fun i(tag: String, msg: String) = logger.i(tag, msg)

        @JvmStatic
        fun i(tag: String, msg: String, tr: Throwable) = logger.i(tag, msg, tr)

        @JvmStatic
        fun w(tag: String, msg: String) = logger.w(tag, msg)

        @JvmStatic
        fun w(tag: String, msg: String, tr: Throwable) = logger.w(tag, msg, tr)


        @JvmStatic
        fun w(tag: String, tr: Throwable) = logger.w(tag, tr)

        @JvmStatic
        fun e(tag: String, msg: String) = logger.e(tag, msg)

        @JvmStatic
        fun e(tag: String, msg: String, tr: Throwable) = logger.e(tag, msg, tr)

    }


}
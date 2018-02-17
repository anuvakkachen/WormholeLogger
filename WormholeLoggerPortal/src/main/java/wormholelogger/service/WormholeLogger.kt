package wormholelogger.service

import android.content.Context
import wormholelogger.service.internal.FirebaseLogHandler

const val PREFERENCE_NAME = "WhLogger"
const val PREFERENCE_KEY = "WhLogger.Package"

/**
 * Configuration for the WormholeLogger service.
 */
class WormholeLogger {

    companion object {

        private var clientPackageName: String? = null
        internal var logHandler: LogHandler = FirebaseLogHandler()
            private set

        /**
         * Initialize the WormholeLogger service
         * @param clientPackage The package name of the client app whose logs needs to be captured.
         */
        @JvmStatic
        fun init(context: Context, clientPackage: String) {
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString(PREFERENCE_KEY, clientPackage).apply()
            clientPackageName = clientPackage
        }

        /**
         * Set the [LogHandler] if you want to custom handle the logs.
         * If not, by default this sends it to your firebase cloud storage (if specified),
         *  if not, then sends it to standard log
         */
        @JvmStatic
        fun setCustomLogHandler(lHandler: LogHandler) {
            logHandler = lHandler
        }

        /**
         * initialize package if not already set
         */
        internal fun init(context: Context) {
            if (clientPackageName == null) {
                clientPackageName = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(PREFERENCE_KEY, null)
            }
        }


        internal fun canAcceptClient(packageName: String?) = (!clientPackageName.isNullOrBlank() && clientPackageName == packageName)


    }


}
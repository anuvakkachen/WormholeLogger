package wormholelogger.service.internal

import android.content.Context
import wormholelogger.service.LogHandler
import wormholelogger.service.internal.buffer.LogBufferManager
import wormholelogger.service.internal.buffer.Pusher

/**
 * [LogHandler] that sends the log to firebase
 */
internal class FirebaseLogHandler(private val context: Context,
                                  private val clientPackageName: String?,
                                  private val googleServicesJsonResId: Int
) : DefaultLogHandler() {

    private var firebaseInitialized = false
    private var logBufferManager: LogBufferManager? = null

    init {
        initFirebaseLogger()
    }

    override fun log(message: String): Int {
        if (firebaseInitialized) {
            logBufferManager?.addLog(message)
        } else {
            super.log(message)
        }
        return 1
    }

    private fun initFirebaseLogger() {
        if (googleServicesJsonResId != -1) {
            val pusher: Pusher? = Pusher.getPusher(context, googleServicesJsonResId)
            pusher?.let {
                firebaseInitialized = pusher.isInitialized()
                logBufferManager = LogBufferManager.getLogBufferManager(context, clientPackageName, pusher)
            }
        }
    }
}
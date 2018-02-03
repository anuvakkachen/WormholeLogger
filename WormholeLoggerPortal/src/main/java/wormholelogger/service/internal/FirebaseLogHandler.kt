package wormholelogger.service.internal

import wormholelogger.common.WhLoggerConstants.LogType
import wormholelogger.service.LogHandler

/**
 * [LogHandler] that sends the log to firebase
 */
internal class FirebaseLogHandler : DefaultLogHandler() {

    private var firebaseInitialized = false


    init {
        //check firebase is set up.
    }


    override fun log(logType: LogType, logTag: String?, message: String?, exLog: Throwable?) {
        if (firebaseInitialized) {
            //todo, send to firebase
        } else {
            super.log(logType, logTag, message, exLog)
        }
    }
}
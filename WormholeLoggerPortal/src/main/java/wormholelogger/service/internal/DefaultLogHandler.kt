package wormholelogger.service.internal

import android.util.Log
import wormholelogger.common.WhLoggerConstants.LogType
import wormholelogger.service.LogHandler

/**
 * Default [LogHandler] that sends the log to standard [Log]
 */
internal open class DefaultLogHandler : LogHandler {

    override fun log(logType: LogType, logTag: String?, message: String?, exLog: Throwable?) {
        when (logType) {
            LogType.WARN -> Log.w(logTag, message, exLog)
            LogType.ERROR -> Log.e(logTag, message, exLog)
            LogType.DEBUG -> Log.d(logTag, message, exLog)
            LogType.VERBOSE -> Log.v(logTag, message, exLog)
            LogType.INFO -> Log.i(logTag, message, exLog)
        }
    }
}
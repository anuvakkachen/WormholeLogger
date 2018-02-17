package wormholelogger.service.internal

import android.util.Log
import wormholelogger.service.LogHandler

/**
 * Default [LogHandler] that sends the log to standard [Log]
 */
internal open class DefaultLogHandler : LogHandler {

    /**
     * Default implementation that logs to Log
     */
    override fun log(message: String) = Log.v("WormHole", message)
}
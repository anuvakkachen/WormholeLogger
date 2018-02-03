package wormholelogger.service

import wormholelogger.common.WhLoggerConstants.LogType

/**
 * Handles the logs from client.
 */
interface LogHandler {

    /**
     * Gets called for a log received from client.
     */
    fun log(logType: LogType, logTag: String?, message: String?, exLog: Throwable?) {
    }
}
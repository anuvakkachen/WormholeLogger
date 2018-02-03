package wormholelogger.service.internal

import wormholelogger.common.WhLogger
import wormholelogger.common.WhLoggerConstants.LogType
import wormholelogger.service.WormholeLogger

/**
 * Implements [WhLogger] to receive the log from client app
 */
internal class WhLoggerImpl : WhLogger {

    private var clientAccepted: Boolean = false

    override fun register(packageName: String?): Boolean {
        clientAccepted = WormholeLogger.canAcceptClient(packageName)
        return clientAccepted
    }

    override fun log(logType: LogType, logTag: String?, message: String?, exLog: Throwable?): Int {
        if (clientAccepted) {
            WormholeLogger.logHandler.log(logType, logTag, message, exLog)
            return 1
        }
        return 0
    }
}
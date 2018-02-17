package wormholelogger.service.internal

import wormholelogger.common.WhLogger
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

    override fun log(log: String): Int {
        if (clientAccepted) {
            return WormholeLogger.logHandler.log(log)
        }
        return 0
    }
}
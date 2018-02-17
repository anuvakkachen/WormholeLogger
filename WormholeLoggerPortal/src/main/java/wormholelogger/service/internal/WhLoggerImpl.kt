package wormholelogger.service.internal

import android.util.Log
import wormholelogger.common.WhLogger
import wormholelogger.service.WormholeLogger

/**
 * Implements [WhLogger] to receive the log from client app
 */
internal class WhLoggerImpl : WhLogger {

    private var clientAccepted: Boolean = false

    override fun register(packageName: String?): Boolean {
        clientAccepted = WormholeLogger.canAcceptClient(packageName)
        Log.v("WhLog", "Checking $packageName $clientAccepted")
        return clientAccepted
    }

    override fun log(log: String): Int {
        if (clientAccepted) {
            return WormholeLogger.logHandler.log(log)
        }
        return 0
    }
}
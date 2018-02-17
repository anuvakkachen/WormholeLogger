package wormholelogger.service

import android.app.Service
import android.content.Intent
import wormholelogger.common.WhLogger_Stub
import wormholelogger.service.internal.WhLoggerImpl


/**
 * Service that transports the client log to the firebase database
 */
class WormholeLoggerService : Service() {

    override fun onCreate() {
        WormholeLogger.init(this)
        super.onCreate()
    }

    override fun onBind(intent: Intent) = WhLogger_Stub(WhLoggerImpl())
}

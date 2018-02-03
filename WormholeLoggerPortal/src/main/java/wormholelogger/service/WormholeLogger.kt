package wormholelogger.service

import wormholelogger.service.internal.FirebaseLogHandler

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
        fun init(clientPackage: String) {
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


        internal fun canAcceptClient(packageName: String?) = (!clientPackageName.isNullOrBlank() && clientPackageName == packageName)


    }


}
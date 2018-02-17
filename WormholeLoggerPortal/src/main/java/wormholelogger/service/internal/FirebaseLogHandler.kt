package wormholelogger.service.internal

import wormholelogger.service.LogHandler

/**
 * [LogHandler] that sends the log to firebase
 */
internal class FirebaseLogHandler : DefaultLogHandler() {

    private var firebaseInitialized = false


    init {
        //check firebase is set up.
    }


    override fun log(message: String): Int {
        if (firebaseInitialized) {
            //todo, send to firebase
        } else {
            super.log(message)
        }
        return 1
    }
}
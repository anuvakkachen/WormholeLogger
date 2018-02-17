package wormholelogger.service

/**
 * Handles the logs from client.
 */
interface LogHandler {

    /**
     * Gets called for a log received from client.
     */
    fun log(message: String): Int
}
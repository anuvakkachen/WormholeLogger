package wormholelogger.service.internal.buffer

import java.io.InputStream

/**
 * Interface that should be implemented by all Log message buffers
 */
interface LogBuffer {

    fun addToBuffer(log: String)

    fun getLogs(): InputStream

    fun getSize(): Int

    fun clearLogs()

    fun isEmpty() : Boolean
}
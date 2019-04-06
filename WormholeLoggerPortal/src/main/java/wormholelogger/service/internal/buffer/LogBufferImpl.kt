package wormholelogger.service.internal.buffer

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * Created by Anu Vakkachen on 5/28/2018.
 */
class LogBufferImpl(val logBufferListener: LogBufferListener) : LogBuffer {

    private val logOutputStream = ByteArrayOutputStream()
    private var currentSize = 0

    companion object {
        const val TAG = "LogBufferImpl"
        const val MAX_BUFFER_SIZE = 100
    }

    override fun addToBuffer(log: String) {
        logOutputStream.write(log.toByteArray())
        ++currentSize

        if (currentSize >= MAX_BUFFER_SIZE) {
            logBufferListener.onBufferFull(this)
        }
    }

    override fun isEmpty(): Boolean = currentSize == 0

    override fun getLogs(): InputStream {
        val byteInStream = ByteArrayInputStream(logOutputStream.toByteArray())
        return byteInStream
    }

    override fun getSize(): Int {
        return currentSize
    }

    override fun clearLogs() {
        currentSize = 0
        logOutputStream.reset()
        logBufferListener.onBufferCleared(this)
    }
}
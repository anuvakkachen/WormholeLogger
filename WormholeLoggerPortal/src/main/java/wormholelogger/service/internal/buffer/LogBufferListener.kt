package wormholelogger.service.internal.buffer

/**
 * Created by Anu Vakkachen on 5/28/2018.
 */
interface LogBufferListener {

    fun onBufferFull(logBuffer: LogBuffer);

    fun onBufferCleared(logBuffer: LogBuffer);
}
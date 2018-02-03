package wormholelogger.common;

import remoter.annotations.Remoter;
import wormholelogger.common.WhLoggerConstants.LogType;

/**
 * Logger interface implemented by the WhLogger service
 */
@Remoter
public interface WhLogger {

    /**
     * Register a client with the service with the given key
     *
     * @param packageName The client packagename
     * @return True if the service is configured to accept for this key
     */
    boolean register(String packageName);

    /**
     * Logs the given message to the service.
     *
     * @param logType The type of the message
     * @param logTag  The log tag
     * @param message The log message
     * @param exLog   Exception if any
     */
    int log(LogType logType, String logTag, String message, Throwable exLog);
}

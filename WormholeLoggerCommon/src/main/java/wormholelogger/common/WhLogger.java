package wormholelogger.common;

import remoter.annotations.Remoter;

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
     * @param log The log message
     */
    int log(String log);
}

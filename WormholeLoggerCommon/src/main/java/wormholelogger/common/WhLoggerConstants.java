package wormholelogger.common;

/**
 * Common constants
 */
public interface WhLoggerConstants {

    String WH_LOGGER_INTENT = "wormholelogger.intent.WormholeLoggerService";

    /**
     * Log Types
     */
    enum LogType {
        INFO,
        VERBOSE,
        DEBUG,
        ERROR,
        WARN
    }

}

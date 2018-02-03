package wormholelogger.log.internal

import wormholelogger.common.WhLogger
import wormholelogger.common.WhLoggerConstants.LogType.*

/**
 * [Logger] implementation that routes to WhLogger remoter
 */
internal class WhLoggerRouter(private val logger: WhLogger) : Logger() {

    override fun i(tag: String, msg: String) = logger.log(INFO, tag, msg, null)

    override fun i(tag: String, msg: String, tr: Throwable) = logger.log(INFO, tag, msg, tr)

    override fun v(tag: String, msg: String) = logger.log(VERBOSE, tag, msg, null)

    override fun v(tag: String, msg: String, tr: Throwable) = logger.log(VERBOSE, tag, msg, tr)

    override fun d(tag: String, msg: String) = logger.log(DEBUG, tag, msg, null)

    override fun d(tag: String, msg: String, tr: Throwable) = logger.log(DEBUG, tag, msg, tr)

    override fun e(tag: String, msg: String) = logger.log(ERROR, tag, msg, null)

    override fun e(tag: String, msg: String, tr: Throwable) = logger.log(ERROR, tag, msg, tr)

    override fun w(tag: String, msg: String) = logger.log(WARN, tag, msg, null)

    override fun w(tag: String, tr: Throwable) = logger.log(WARN, tag, "", tr)

    override fun w(tag: String, msg: String, tr: Throwable) = logger.log(WARN, tag, msg, tr)

}
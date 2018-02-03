package wormholelogger.log.internal

import android.util.Log

/**
 * Internal default logger that logs to standard [Log]
 */
internal open class Logger {

    open fun v(tag: String, msg: String) = Log.v(tag, msg)

    open fun v(tag: String, msg: String, tr: Throwable) = Log.v(tag, msg, tr)

    open fun d(tag: String, msg: String) = Log.d(tag, msg)

    open fun d(tag: String, msg: String, tr: Throwable) = Log.d(tag, msg, tr)

    open fun i(tag: String, msg: String) = Log.i(tag, msg)

    open fun i(tag: String, msg: String, tr: Throwable) = Log.i(tag, msg, tr)

    open fun w(tag: String, msg: String) = Log.w(tag, msg)

    open fun w(tag: String, msg: String, tr: Throwable) = Log.w(tag, msg, tr)

    open fun w(tag: String, tr: Throwable) = Log.w(tag, tr)

    open fun e(tag: String, msg: String) = Log.e(tag, msg)

    open fun e(tag: String, msg: String, tr: Throwable) = Log.e(tag, msg, tr)

}
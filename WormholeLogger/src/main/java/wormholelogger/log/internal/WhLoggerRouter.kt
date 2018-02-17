package wormholelogger.log.internal

import wormholelogger.common.WhLogger
import wormholelogger.common.WhLoggerConstants
import wormholelogger.common.WhLoggerConstants.LogType.*
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

/**
 * [Logger] implementation that routes to WhLogger remoter
 */
internal class WhLoggerRouter(private val logger: WhLogger, private val packageName: String) : Logger() {

    private val logMessageBuilder = StringBuffer()
    private val calendar = Calendar.getInstance()


    override fun i(tag: String, msg: String) = formatAndLog(INFO, tag, msg, null)

    override fun i(tag: String, msg: String, tr: Throwable) = formatAndLog(INFO, tag, msg, tr)

    override fun v(tag: String, msg: String) = formatAndLog(VERBOSE, tag, msg, null)

    override fun v(tag: String, msg: String, tr: Throwable) = formatAndLog(VERBOSE, tag, msg, tr)

    override fun d(tag: String, msg: String) = formatAndLog(DEBUG, tag, msg, null)

    override fun d(tag: String, msg: String, tr: Throwable) = formatAndLog(DEBUG, tag, msg, tr)

    override fun e(tag: String, msg: String) = formatAndLog(ERROR, tag, msg, null)

    override fun e(tag: String, msg: String, tr: Throwable) = formatAndLog(ERROR, tag, msg, tr)

    override fun w(tag: String, msg: String) = formatAndLog(WARN, tag, msg, null)

    override fun w(tag: String, tr: Throwable) = formatAndLog(WARN, tag, "", tr)

    override fun w(tag: String, msg: String, tr: Throwable) = formatAndLog(WARN, tag, msg, tr)


    /**
     * Format the message and send it to remote
     */
    private fun formatAndLog(logType: WhLoggerConstants.LogType, logTag: String?, message: String?, exLog: Throwable?): Int {
        //date time PID-TID/package priority/tag: message
        //12-10 13:02:50.071 1901-4229/com.google.android.gms V/AuthZen: Handling delegate intent.
        logMessageBuilder.setLength(0)
        calendar.timeInMillis = System.currentTimeMillis()
        appendWithProperLength(calendar.get(Calendar.MONTH) + 1, 2)
                .append('-')
        appendWithProperLength(calendar.get(Calendar.DAY_OF_MONTH), 2)
                .append(' ')
        appendWithProperLength(calendar.get(Calendar.HOUR_OF_DAY), 2)
                .append(':')
        appendWithProperLength(calendar.get(Calendar.MINUTE), 2)
                .append(':')
        appendWithProperLength(calendar.get(Calendar.SECOND), 2)
                .append('.')
        appendWithProperLength(calendar.get(Calendar.MILLISECOND), 3)
                .append(' ')
                .append(android.os.Process.myPid())
                .append('-')
                .append(Thread.currentThread().id)
                .append('/')
                .append(packageName)
                .append(' ')
                .append(logType.name[0])
                .append('/')
                .append(logTag ?: "TAG")
                .append(':')
                .append(' ')
                .append(message ?: "")

        if (exLog != null) {
            logMessageBuilder.append('\n')
            val stringWriter = StringWriter()
            val exWriter = PrintWriter(stringWriter)
            exLog.printStackTrace(exWriter)
            logMessageBuilder.append(stringWriter.buffer.toString())
        }
        logMessageBuilder.append('\n')

        logger.log(logMessageBuilder.toString())
        return 1
    }

    private fun appendWithProperLength(number: Int, size: Int): StringBuffer {
        if (size == 3) {
            if (number < 10) {
                logMessageBuilder.append("00")
            } else if (number < 100) {
                logMessageBuilder.append("0")
            }
        } else if (size == 2) {
            if (number < 10) {
                logMessageBuilder.append("0")
            }
        }
        logMessageBuilder.append(number)
        return logMessageBuilder
    }


}
package wormholelogger.service.internal.buffer

import android.content.Context
import androidx.work.*
import wormholelogger.service.PREFERENCE_KEY
import wormholelogger.service.PREFERENCE_NAME
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val DEFAULT_FILE_NAME = "portal"
const val PREFERENCE_KEY_LAST_PUSH = "WhLogger.lastPush"
const val SEND_LOGS_WORK_NAME = "WhLogger.sendLogsWork"

/**
 * Created by Anu Vakkachen on 5/28/2018.
 */
class LogBufferManager : LogBufferListener {

    private var logBuffer: LogBuffer = LogBufferImpl(this)
    private val clientPackageName : String?
    private val context : Context
    private val pusher: Pusher
    private var uniqueId : String? = null
    private val workManager: WorkManager = WorkManager.getInstance()

    companion object {
        private val TAG = "LogBufferManager"
        private val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss")
        private val lock = Any()
        private var logBufferManager: LogBufferManager? = null

        internal fun getLogBufferManager(context: Context,
                                         clientPackageName : String?,
                                         pusher: Pusher): LogBufferManager? {
            if (logBufferManager == null) {
                synchronized(lock) {
                    if (logBufferManager == null) {
                        logBufferManager = LogBufferManager(context, pusher)
                    }
                }
            }
            return logBufferManager
        }
    }

    private constructor(context: Context,
                        pusher: Pusher){
        this.context = context
        this.pusher = pusher
        clientPackageName = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE).getString(PREFERENCE_KEY, DEFAULT_FILE_NAME)
    }

    override fun onBufferFull(logBuffer: LogBuffer) {
        flush()
    }

    override fun onBufferCleared(logBuffer: LogBuffer) {
    }

    private fun flush(){
        if(logBuffer.isEmpty()){
            return
        }

        pusher?.let {
            if (it.isInitialized()) {
                val timeNow = System.currentTimeMillis()
                val formattedTime = dateFormat.format(Date(timeNow))
                val fileName = " $clientPackageName /  $uniqueId / $formattedTime .txt"
                pusher.pushToStorage(fileName, logBuffer.getLogs())
                logBuffer.clearLogs();
                context.getSharedPreferences(
                        PREFERENCE_NAME, Context.MODE_PRIVATE)
                        .edit().putLong(PREFERENCE_KEY_LAST_PUSH, timeNow).apply()
            }
        }
    }

    fun addLog(message: String) {
        if(logBuffer.isEmpty()){
            scheduleWork()
        }
        logBuffer.addToBuffer(message + "\n")
    }

    private fun scheduleWork(){
        val workReq = OneTimeWorkRequest.Builder(PushWorker::class.java)
                .setInitialDelay(15,TimeUnit.MINUTES).build()
        workManager.enqueue(workReq)
    }

    /**
     * Worker that pushes logs if last push was before time interval
     */
    class PushWorker(private var ctx: Context, params: WorkerParameters) :  Worker(ctx, params){

        private val FIFTEEN_MINS = 15 * 60 * 1000

        override fun doWork(): Result {
            val lastPush = ctx.getSharedPreferences(
                    PREFERENCE_NAME, Context.MODE_PRIVATE).getLong(PREFERENCE_KEY_LAST_PUSH, 0)
            val timeNow = System.currentTimeMillis()
            if(timeNow - lastPush > FIFTEEN_MINS){
                return try{
                    logBufferManager?.flush()
                    Result.success()
                } catch (th : Throwable){
                    Result.failure()
                }
            }
            return Result.success()
        }
    }
}
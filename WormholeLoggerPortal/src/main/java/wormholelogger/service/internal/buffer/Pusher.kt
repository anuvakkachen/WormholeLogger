package wormholelogger.service.internal.buffer

import android.content.Context
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import wormholelogger.service.PREFERENCE_KEY_RES_ID
import wormholelogger.service.PREFERENCE_NAME
import wormholelogger.service.internal.util.Parser
import wormholelogger.service.internal.util.ResReader
import java.io.InputStream

/**
 * Created by Anu Vakkachen on 5/28/2018.
 *
 * Pushes logs
 */
class Pusher {

    private lateinit var firebaseStorage: FirebaseStorage
    private val context: Context
    private val googleServicesJsonResId: Int

    private constructor(context: Context,
                        googleServicesJsonResId: Int) {
        this.context = context
        this.googleServicesJsonResId = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE).getInt(PREFERENCE_KEY_RES_ID, -1)
        initFirebase()
    }

    companion object {
        private const val TAG = "Pusher"
        private var firebaseInitialized = false
        private val lock = Any()
        var pusher: Pusher? = null

        /**
         * Returns the singleton
         */
        internal fun getPusher(context: Context, googleServicesJsonResId: Int): Pusher? {
            if (pusher == null) {
                synchronized(lock) {
                    if (pusher == null) {
                        pusher = Pusher(context, googleServicesJsonResId)
                    }
                }
            }
            return pusher
        }
    }

    internal fun isInitialized(): Boolean {
        return firebaseInitialized
    }

    private fun initFirebase() {
        val firebaseApp: FirebaseApp? = initFirebaseAppFromFile()

        firebaseApp?.let {
            try {
                firebaseStorage = FirebaseStorage.getInstance(firebaseApp)
                firebaseInitialized = true
            } catch (istEx: IllegalStateException) {
                firebaseInitialized = false
                android.util.Log.d("Pusher", " firebase storage creation error. IllegalState." +
                        " initialized false, error = " + istEx.message)
            }
        }
    }

    private fun initFirebaseAppFromFile(): FirebaseApp? {
        val resReader = ResReader(context)
        val json = resReader.readResourceFile(googleServicesJsonResId)
        val parser = Parser(json)
        val firebaseOptions = parser.parse(context.packageName)

        firebaseOptions?.let { return FirebaseApp.initializeApp(context, firebaseOptions) }
        return null
    }

    fun pushToStorage(fileName: String, inputStream: InputStream) {
        val storageRef: StorageReference = firebaseStorage.reference
        val fileRef: StorageReference = storageRef.child(fileName)

        val metadata = StorageMetadata.Builder()
                .setContentType("text/plain")
                .build()

        val uploadTask: UploadTask = fileRef.putStream(inputStream, metadata)
        uploadTask.addOnFailureListener(object : OnFailureListener {
            override fun onFailure(ex: Exception) {
                android.util.Log.w(TAG, "Firebase upload failed: " + ex.message)
            }

        }).addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
            override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot?) {
            }
        })
    }
}
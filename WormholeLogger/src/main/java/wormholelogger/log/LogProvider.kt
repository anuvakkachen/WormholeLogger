package wormholelogger.log

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Context grabber
 */
class LogProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        Log.init(context)
        return true
    }

    override fun insert(p0: Uri?, p1: ContentValues?): Uri? = null
    override fun query(p0: Uri?, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor? = null
    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?) = 0
    override fun delete(p0: Uri?, p1: String?, p2: Array<out String>?) = 0
    override fun getType(p0: Uri?) = ""

}
package wormholelogger.service.internal.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class ResReader(val context: Context) {

    fun readResourceFile(resFileId: Int): String {
        val inStream = context.resources.openRawResource(resFileId)
        return readStream(inStream)
    }

    private fun readStream(inStream: InputStream): String {
        var strBuilder = StringBuilder()
        val bReader = BufferedReader(InputStreamReader(inStream))
        var lineRead: String? = ""

        do {
            lineRead = bReader.readLine()
            lineRead?.let {
                strBuilder.append(it)
            }
        } while (lineRead != null)

        return strBuilder.toString()
    }
}
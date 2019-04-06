package wormholelogger.service.internal.util

import com.google.firebase.FirebaseOptions
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class Parser(val json: String) {

    var moshi = Moshi.Builder().build()

    fun parse(packageName: String): FirebaseOptions? {
        val adapter: JsonAdapter<Root> = moshi.adapter(Root::class.java)
        val rootNode: Root? = adapter.fromJson(json)

        if (rootNode != null) {
            val fbOptionsBuilder = FirebaseOptions.Builder()
            fbOptionsBuilder.let {
                it.setDatabaseUrl(rootNode?.project_info?.firebase_url)
                it.setStorageBucket(rootNode?.project_info?.storage_bucket)

                for (client in rootNode.client) {
                    if (client.client_info?.android_client_info?.package_name.equals(packageName)) {
                        client.api_key?.get(0)?.current_key?.let {
                            fbOptionsBuilder.setApiKey(it)
                        }
                        client.client_info?.mobilesdk_app_id?.let {
                            fbOptionsBuilder.setApplicationId(it)
                        }
                        return fbOptionsBuilder.build()
                    }
                }
            }
        }
        return null
    }
}
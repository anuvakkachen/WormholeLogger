package wormholelogger.service.internal.util

data class Root(var project_info: ProjectInfo?,
                var client: List<Client>,
                var configuration_version: Int = 0) {
}

data class ProjectInfo(var project_number: String?,
                       var firebase_url: String?,
                       var project_id: String?,
                       var storage_bucket: String?) {
}

data class Client(var client_info: ClientInfo?,
                  var oauth_client: List<OAuthClient>?,
                  var api_key: List<ApiKey>?) {

}

data class ClientInfo(var mobilesdk_app_id: String?,
                      var android_client_info: AndroidClientInfo?) {
}

//Don't need it now
class OAuthClient {
}

data class ApiKey(var current_key: String?) {
}

data class AndroidClientInfo(var package_name: String?) {
}
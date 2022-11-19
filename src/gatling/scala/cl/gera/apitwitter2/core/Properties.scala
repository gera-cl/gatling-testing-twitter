package cl.gera.apitwitter2.core

object Properties {
  object Twitter {
    val baseUrl: String = "https://api.twitter.com/2"
    val client_id = new Property("TW_CLIENT_ID")
    val client_secret = new Property("TW_CLIENT_SECRET")
    val access_token = new Property("TW_ACCESS_TOKEN")
    val refresh_token = new Property("TW_REFRESH_TOKEN")
  }

  object Github {
    val baseUrl: String = "https://api.github.com"
    val token = new Property("GH_ACCESS_TOKEN")
    val repo = new Property("GH_REPOSITORY")
    val repoPublicKey = new Property("GH_REPOSITORY_PUBLIC_KEY")
    val repoKeyId = new Property("GH_REPOSITORY_KEY_ID")
  }
}

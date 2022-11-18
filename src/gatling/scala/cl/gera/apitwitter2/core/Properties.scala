package cl.gera.apitwitter2.core

object Properties {
  object Twitter {
    val baseUrl: String = "https://api.twitter.com/2"
    val client_id = new Property("TWITTER_CLIENT_ID")
    val client_secret = new Property("TWITTER_CLIENT_SECRET")
    val access_token = new Property("TWITTER_ACCESS_TOKEN")
    val refresh_token = new Property("TWITTER_REFRESH_TOKEN")
  }

  object Github {
    val baseUrl: String = "https://api.github.com"
    val token = new Property("GITHUB_ACCESS_TOKEN")
    val repo = new Property("GITHUB_REPOSITORY")
    val owner = new Property("GITHUB_OWNER")
    val repoPublicKey = new Property("GITHUB_REPOSITORY_PUBLIC_KEY")
    val repoKeyId = new Property("GITHUB_REPOSITORY_KEY_ID")
  }
}

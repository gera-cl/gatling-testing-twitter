package cl.gera.apitwitter2.shared

object Properties {
  val client_id: String = getEnv("TWITTER_CLIENT_ID")
  val client_secret: String = getEnv("TWITTER_CLIENT_SECRET")
  val access_token: String = getEnv("TWITTER_ACCESS_TOKEN")
  val refresh_token: String = getEnv("TWITTER_REFRESH_TOKEN")

  private def getEnv(name: String): String = {
    val value = System.getenv(name)
    if (value == null)
      throw new RuntimeException("Environment variable \"%s\" not found.".format(name))
    value
  }
}

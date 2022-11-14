package cl.gera.apitwitter2.shared

object Bodies {
  private val root = s"${IDEPathHelper.gradleResourcesDirectory.toString}/bodies"
  private def getFileBody(fileName: String) = s"$root/$fileName"

  val newTweet: String = getFileBody("newTweet.json")
}

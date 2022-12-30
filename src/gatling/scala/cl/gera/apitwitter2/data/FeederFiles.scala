package cl.gera.apitwitter2.data

import cl.gera.apitwitter2.core.IDEPathHelper

object FeederFiles {
  private val rootPath = s"${IDEPathHelper.gradleBuildDirectory.toString}/resources/gatling/feeders"
  val twitterUsers = s"${rootPath}/twitter-users.csv"
  val tweets = s"${rootPath}/tweets.csv"

}

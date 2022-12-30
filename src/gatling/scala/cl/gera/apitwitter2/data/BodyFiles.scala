package cl.gera.apitwitter2.data

import cl.gera.apitwitter2.core.IDEPathHelper

object BodyFiles {
  private val rootPath = s"${IDEPathHelper.gradleBuildDirectory.toString}/resources/gatling/bodies"
  val textTweet = s"$rootPath/text-tweet.json"
  val replyTweet = s"$rootPath/reply-tweet.json"
  val pollTweet = s"$rootPath/poll-tweet.json"
}

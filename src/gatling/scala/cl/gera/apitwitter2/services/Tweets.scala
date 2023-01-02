package cl.gera.apitwitter2.services

import cl.gera.apitwitter2.core.Service
import cl.gera.apitwitter2.data.BodyFiles
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

object Tweets extends Service {
  override val path: String = "/tweets"

  private def postTweet(requestName: String, fileBody: String): ChainBuilder = {
    exec(
      http(requestName)
        .post(s"$path")
        .body(ElFileBody(fileBody))
        .asJson
        .check(
          status.is(201),
          jsonPath("""$.data.id""").exists.saveAs("newTweetId")
        )
    )
      .exitHereIfFailed
      // Save tweets created in session
      .exec(session => {
        val keyName = "newTweets"
        val tweetId = session("newTweetId").as[String]
        if (!session.contains(keyName)) {
          session.set(keyName, Seq[String](tweetId))
        } else {
          session.set(keyName, session(keyName).as[Seq[String]] :+ tweetId)
        }
      })
  }

  def postTextTweet(text: String): ChainBuilder = {
    exec(session => session.set("tweetText", text))
      .exec(postTweet("Text Tweet", BodyFiles.textTweet))
  }

  def replyTweet(targetTweetId: String, text: String): ChainBuilder = {
    exec(session => session.set("tweetText", text).set("targetTweetId", targetTweetId))
      .exec(postTweet("Reply a Tweet", BodyFiles.replyTweet))
  }

  def postThreadTweet(tweetText: String*): ChainBuilder = {
    var chainBuilder = exec(postTextTweet(tweetText(0)))
    for (i <- 1 until tweetText.length) {
      chainBuilder = chainBuilder
        .exec(session => {
          val targetTweetId = session("newTweetId").as[String]
          session.set("targetTweetId", targetTweetId).set("tweetText", tweetText(i))
        })
        .exec(postTweet("Post a Tweet - Thread", BodyFiles.replyTweet))
    }
    chainBuilder
  }

  def postPollTweet(text: String): ChainBuilder = {
    exec(session => session.set("tweetText", text))
      .exec(postTweet("Post a Tweet - Poll", BodyFiles.pollTweet))
  }

  def deleteTweet(tweetId: String): ChainBuilder = {
    exec(
      http("Delete a tweet")
        .delete(s"$path/$tweetId")
        .check(
          status.is(200),
          jsonPath("""$.data.deleted""").ofType[Boolean].is(true)
        )
    ).exitHereIfFailed
  }

  def getTweetById(tweetId: String): ChainBuilder = {
    exec(
      http("Tweet lookup")
        .get(s"$path/$tweetId")
        .check(
          status.is(200)
        )
    ).exitHereIfFailed
  }
}

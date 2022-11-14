package cl.gera.apitwitter2.services

import cl.gera.apitwitter2.shared.Service
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object Tweets extends Service {
  override val path: String = "/tweets"

  def createTweet(fileBody: String): HttpRequestBuilder = {
    http("Create a tweet")
      .post(s"$path")
      .body(ElFileBody(fileBody))
      .asJson
      .check(
        status.is(201),
        jsonPath("""$.data.id""").exists.saveAs("newTweetId")
      )
  }

  def deleteTweet(tweetId: String): HttpRequestBuilder = {
    http("Delete a tweet")
      .delete(s"$path/$tweetId")
      .check(
        status.is(200),
        jsonPath("""$.data.deleted""").ofType[Boolean].is(true)
      )
  }
}

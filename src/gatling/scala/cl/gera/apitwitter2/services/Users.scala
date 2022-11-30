package cl.gera.apitwitter2.services

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import cl.gera.apitwitter2.core.Service

object Users extends Service {
  override val path: String = "/users"

  def me(): HttpRequestBuilder = {
    http("Authenticated user lookup")
      .get(s"$path/me")
      .check(
        status.is(200),
        jsonPath("""$.data.id""").exists.saveAs("myUserId")
      )
  }

  def getUserByUsername(username: String): HttpRequestBuilder = {
    http("Get a user by username")
      .get(s"$path/by/username/$username")
      .check(
        status.is(200),
        jsonPath("""$.data.id""").exists.saveAs("userId")
      )
  }

  def follow(sourceUserId: String, targetUserId: String): HttpRequestBuilder = {
    http("Follow a user")
      .post(s"$path/$sourceUserId/following")
      .body(StringBody(s"""{ "target_user_id": "$targetUserId"}"""))
      .asJson
      .check(
        status.is(200)
      )
  }

  def unfollow(sourceUserId: String, targetUserId: String): HttpRequestBuilder = {
    http("Unfollow a user")
      .delete(s"$path/$sourceUserId/following/$targetUserId")
      .asJson
      .check(
        status.is(200)
      )
  }

  def getTweetsByUserId(userId: String): HttpRequestBuilder = {
    http("User Tweet timeline")
      .get(s"$path/$userId/tweets")
      .check(
        status.is(200),
        jsonPath("""$.data[*].id""").findRandom.saveAs("tweetId")
      )
  }

  def likeTweet(sourceUserId: String, tweetId: String): HttpRequestBuilder = {
    http("Like a tweet")
      .post(s"$path/$sourceUserId/likes")
      .body(StringBody(s"""{ "tweet_id": "$tweetId"}"""))
      .asJson
      .check(
        status.is(200)
      )
  }

  def retweet(sourceUserId: String, tweetId: String): HttpRequestBuilder = {
    http("Retweet")
      .post(s"$path/$sourceUserId/retweets")
      .body(StringBody(s"""{ "tweet_id": "$tweetId"}"""))
      .asJson
      .check(
        status.is(200)
      )
  }

  def bookmarkTweet(sourceUserId: String, tweetId: String): HttpRequestBuilder = {
    http("Bookmark a tweet")
      .post(s"$path/$sourceUserId/bookmarks")
      .body(StringBody(s"""{ "tweet_id": "$tweetId"}"""))
      .asJson
      .check(
        status.is(200)
      )
  }

  def listBookmarks(sourceUserId: String): HttpRequestBuilder = {
    http("List bookmarks")
      .get(s"$path/$sourceUserId/bookmarks")
      .asJson
      .check(
        status.is(200)
      )
  }
}

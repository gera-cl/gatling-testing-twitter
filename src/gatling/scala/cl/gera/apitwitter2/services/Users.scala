package cl.gera.apitwitter2.services

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import cl.gera.apitwitter2.core.Service
import io.gatling.core.structure.ChainBuilder

object Users extends Service {
  override val path: String = "/users"

  def me(): ChainBuilder = {
    exec(
      http("Authenticated user lookup")
      .get(s"$path/me")
      .check(
        status.is(200),
        jsonPath("""$.data.id""").exists.saveAs("myUserId")
      )
    ).exitHereIfFailed
  }

  def userLookupByUsername(username: String): ChainBuilder = {
    exec(
      http("Get a user by username")
      .get(s"$path/by/username/$username")
      .formParam("user.fields", "created_at,description,entities,id,location," +
        "name,pinned_tweet_id,profile_image_url,protected,public_metrics," +
        "url,username,verified,withheld")
      .check(
        status.is(200),
        jsonPath("""$.data.id""").exists.saveAs("userId")
      )
    ).exitHereIfFailed
  }

  def follow(sourceUserId: String, targetUserId: String): ChainBuilder = {
    exec(
      http("Follow a user")
      .post(s"$path/$sourceUserId/following")
      .body(StringBody(s"""{ "target_user_id": "$targetUserId"}"""))
      .asJson
      .check(
        status.is(200)
      )
    ).exitHereIfFailed
  }

  def unfollow(sourceUserId: String, targetUserId: String): ChainBuilder = {
    exec(
      http("Unfollow a user")
      .delete(s"$path/$sourceUserId/following/$targetUserId")
      .asJson
      .check(
        status.is(200)
      )
    ).exitHereIfFailed
  }

  def getTweetsByUserId(userId: String, findAll: Boolean = true, findRandom: Boolean = false): ChainBuilder = {
    exec(
      http("User Tweet timeline")
      .get(s"$path/$userId/tweets")
      .check(status.is(200))
      .checkIf(findAll) {
        jsonPath("""$.data[*].id""").findAll.saveAs("tweetsIds")
      }
      .checkIf(findRandom) {
        jsonPath("""$.data[*].id""").findRandom.saveAs("tweetId")
      }
    ).exitHereIfFailed
  }

  def getReverseChronologicalTimeline(userId: String): ChainBuilder = {
    exec(
      http("Reverse Chronological Home Timeline")
      .get(s"$path/$userId/timelines/reverse_chronological")
      .formParam("exclude", "retweets,replies")
      .check(
        status.is(200),
        jsonPath("""$.data[*].id""").findAll.saveAs("tweetsIds")
      )
    ).exitHereIfFailed
  }

  def likeTweet(sourceUserId: String, tweetId: String): ChainBuilder = {
    exec(
      http("Like a tweet")
      .post(s"$path/$sourceUserId/likes")
      .body(StringBody(s"""{ "tweet_id": "$tweetId"}"""))
      .asJson
      .check(
        status.is(200)
      )
    ).exitHereIfFailed
  }

  def retweet(sourceUserId: String, tweetId: String): ChainBuilder = {
    exec(
      http("Retweet")
      .post(s"$path/$sourceUserId/retweets")
      .body(StringBody(s"""{ "tweet_id": "$tweetId"}"""))
      .asJson
      .check(
        status.is(200)
      )
    ).exitHereIfFailed
  }

  def bookmarkTweet(sourceUserId: String, tweetId: String): ChainBuilder = {
    exec(
      http("Bookmark a tweet")
      .post(s"$path/$sourceUserId/bookmarks")
      .body(StringBody(s"""{ "tweet_id": "$tweetId"}"""))
      .asJson
      .check(
        status.is(200)
      )
    ).exitHereIfFailed
  }

  def listBookmarks(sourceUserId: String): ChainBuilder = {
    exec(
      http("List bookmarks")
      .get(s"$path/$sourceUserId/bookmarks")
      .asJson
      .check(
        status.is(200)
      )
    ).exitHereIfFailed
  }
}

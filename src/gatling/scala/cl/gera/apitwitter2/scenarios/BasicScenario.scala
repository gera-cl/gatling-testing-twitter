package cl.gera.apitwitter2.scenarios

import cl.gera.apitwitter2.data.FeederFiles
import cl.gera.apitwitter2.services.{Tweets, Users}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

import scala.collection.immutable.ArraySeq.unsafeWrapArray
import scala.util.Random

object BasicScenario {
  private val myUserId = "#{myUserId}"
  private val targetUsername = "#{username}"
  private val targetUserId = "#{userId}"
  private val selectedTweet = "#{selectedTweet}"

  def default(networkRepetitions: Int = 1, timelineRepetitions: Int = 1): ScenarioBuilder =
    scenario("Basic Scenario")
      .exec(Users.me())
      .group("Network") {
        repeat(networkRepetitions) {
          feed(csv(FeederFiles.twitterUsers).shuffle)
            .exec(Users.userLookupByUsername(targetUsername))
            .exec(Users.getTweetsByUserId(targetUserId))
            .exec(Users.follow(myUserId, targetUserId))
        }
      }
      .group("Timeline") {
        exec(Users.getReverseChronologicalTimeline(myUserId))
          .repeat(timelineRepetitions) {
            exec(session => {
              val tweetsIds = session("tweetsIds").as[Seq[String]]
              session.set("selectedTweet", tweetsIds(Random.nextInt(tweetsIds.length)))
            })
              .exitHereIfFailed
              .exec(Tweets.getTweetById(selectedTweet))
              .exec(Users.likeTweet(myUserId, selectedTweet))
              .exec(Users.retweet(myUserId, selectedTweet))
              .exec(Users.bookmarkTweet(myUserId, selectedTweet))
          }
      }
      .group("Tweeting") {
        exec(Tweets.postTextTweet(tweetText))
          .exec(Tweets.postThreadTweet(unsafeWrapArray(treadTweets): _*))
          .exec(Tweets.postPollTweet(s"A simple poll\\n\\n${System.currentTimeMillis()}"))
      }

  private def tweetText = s"Performance Testing using Gatling\\n\\n${System.currentTimeMillis()}"

  private def treadTweets: Array[String] = Array(
    s"1. Lorem \\n\\n${System.currentTimeMillis()}",
    s"2. ipsum\\n\\n${System.currentTimeMillis()}",
    s"3. dolor\\n\\n${System.currentTimeMillis()}"
  )
}

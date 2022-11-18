package cl.gera.apitwitter2.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import cl.gera.apitwitter2.core.Bodies
import cl.gera.apitwitter2.services.Tweets

object ManageTweets {
  val createAndDeleteTweet: ScenarioBuilder = {
    scenario("Create and Delete a tweet")
      .exec(Tweets.createTweet(Bodies.newTweet))
      .exitHereIfFailed
      .pause(15)
      .exec(Tweets.deleteTweet("#{newTweetId}"))
  }
}

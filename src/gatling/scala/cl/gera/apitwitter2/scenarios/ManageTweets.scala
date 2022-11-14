package cl.gera.apitwitter2.scenarios

import cl.gera.apitwitter2.services.Tweets
import cl.gera.apitwitter2.shared.Bodies
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

object ManageTweets {
  val createAndDeleteTweet: ScenarioBuilder = {
    scenario("Create and Delete a tweet")
      .exec(Tweets.createTweet(Bodies.newTweet))
      .exitHereIfFailed
      .pause(15)
      .exec(Tweets.deleteTweet("#{newTweetId}"))
  }
}

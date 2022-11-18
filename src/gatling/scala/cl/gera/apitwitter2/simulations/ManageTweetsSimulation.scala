package cl.gera.apitwitter2.simulations

import io.gatling.core.Predef._
import cl.gera.apitwitter2.core.TwitterSimulation
import cl.gera.apitwitter2.scenarios.ManageTweets

class ManageTweetsSimulation extends TwitterSimulation {
  setUp(ManageTweets.createAndDeleteTweet.inject(atOnceUsers(1))).protocols(httpProtocol)
}

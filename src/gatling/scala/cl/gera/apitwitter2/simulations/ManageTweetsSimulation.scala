package cl.gera.apitwitter2.simulations

import io.gatling.core.Predef._
import cl.gera.apitwitter2.scenarios.ManageTweets
import cl.gera.apitwitter2.shared.TwitterSimulation

class ManageTweetsSimulation extends TwitterSimulation {
  setUp(ManageTweets.createAndDeleteTweet.inject(atOnceUsers(1))).protocols(httpProtocol)
}

package cl.gera.apitwitter2.simulations

import io.gatling.core.Predef._
import cl.gera.apitwitter2.core.TwitterSimulation
import cl.gera.apitwitter2.scenarios.BasicScenario

class BasicSimulation extends TwitterSimulation {
  setUp(BasicScenario.default().inject(atOnceUsers(1))).protocols(httpProtocol)
}

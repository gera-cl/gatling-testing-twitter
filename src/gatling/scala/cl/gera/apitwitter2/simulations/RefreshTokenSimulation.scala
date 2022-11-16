package cl.gera.apitwitter2.simulations

import io.gatling.core.Predef._
import cl.gera.apitwitter2.scenarios.Authentication
import cl.gera.apitwitter2.shared.TwitterSimulation

class RefreshTokenSimulation extends TwitterSimulation {
  setUp(Authentication.refreshToken.inject(atOnceUsers(1))).protocols(publicHttpProtocol)
}

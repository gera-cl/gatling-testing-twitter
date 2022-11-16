package cl.gera.apitwitter2.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import cl.gera.apitwitter2.services.OAuth2

object Authentication {
  val refreshToken: ScenarioBuilder = {
    scenario("Refresh Token")
      .exec(OAuth2.refreshToken)
      .exitHereIfFailed
      .exec(session => {
        println(s"access_token->${session("access_token").as[String]}")
        println(s"refresh_token->${session("refresh_token").as[String]}")
        session
      })
  }
}

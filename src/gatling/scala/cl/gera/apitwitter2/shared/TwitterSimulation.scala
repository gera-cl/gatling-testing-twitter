package cl.gera.apitwitter2.shared

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class TwitterSimulation extends Simulation {
  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://api.twitter.com/2")
    .authorizationHeader(s"Bearer ${Properties.access_token}")

  val publicHttpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://api.twitter.com/2")
}

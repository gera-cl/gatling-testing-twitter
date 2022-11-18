package cl.gera.apitwitter2.core

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class TwitterSimulation extends Simulation {
  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(Properties.Twitter.baseUrl)
    .authorizationHeader(s"Bearer ${Properties.Twitter.access_token}")
}

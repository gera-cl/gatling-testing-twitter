package cl.gera.apitwitter2.shared

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import java.util.Objects.requireNonNull

class TwitterSimulation extends Simulation {
  private val token = requireNonNull(System.getenv("TWITTER_API_TOKEN"))

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://api.twitter.com/2")
    .authorizationHeader(s"Bearer $token")
}

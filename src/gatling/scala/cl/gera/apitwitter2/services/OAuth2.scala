package cl.gera.apitwitter2.services

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import cl.gera.apitwitter2.shared.{Properties, Service}

import java.util.Base64
import java.nio.charset.StandardCharsets

object OAuth2 extends Service {
  override val path: String = "/oauth2"

  def refreshToken: HttpRequestBuilder = {
    val authorization = Base64.getEncoder.encodeToString(
      s"${Properties.client_id}:${Properties.client_secret}".getBytes(StandardCharsets.UTF_8)
    )

    http("Refresh Token")
      .post(s"$path/token")
      .header("Authorization", s"Basic $authorization")
      .formParamMap(
        Map(
          "grant_type" -> "refresh_token",
          "client_id" -> Properties.client_id,
          "refresh_token" -> Properties.refresh_token
        )
      )
      .asFormUrlEncoded
      .check(
        status.is(200),
        jsonPath("$.access_token").exists.saveAs("access_token"),
        jsonPath("$.refresh_token").exists.saveAs("refresh_token")
      )
  }
}

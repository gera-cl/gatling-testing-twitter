package cl.gera.apitwitter2.auth

import cl.gera.apitwitter2.core.Properties

import java.net.{URI, URLEncoder}
import java.net.http.HttpResponse.BodyHandlers
import java.net.http.{HttpClient, HttpRequest}
import java.nio.charset.StandardCharsets
import java.util.Base64
import com.fasterxml.jackson.databind.ObjectMapper

object TwitterOAuth2 {
  private val baseUrl = Properties.Twitter.baseUrl
  private val path = s"/oauth2"
  private val httpClient = HttpClient.newBuilder().build()

  def getRefreshToken(clientId: String, clientSecret: String, refreshToken: String): TwitterTokens = {
    val authorization: String = Base64.getEncoder.encodeToString(
      s"$clientId:$clientSecret".getBytes(StandardCharsets.UTF_8)
    )

    val params = Map(
      "grant_type" -> "refresh_token",
      "client_id" -> clientId,
      "refresh_token" -> refreshToken
    )

    val request = HttpRequest.newBuilder()
      .uri(new URI(s"$baseUrl$path/token"))
      .headers(
        "Content-Type", "application/x-www-form-urlencoded",
        "Authorization", s"Basic $authorization"
      )
      .POST(getParamsUrlEncoded(params))
      .build()

    val response = httpClient.send(request, BodyHandlers.ofString())
    // Validate status code
    val statusCode = response.statusCode()
    if (statusCode != 200)
      throw new RuntimeException(s"Unexpected status $statusCode in Twitter authentication")

    // Parse response body
    val responseBodyAsString = response.body()
    val mapper = new ObjectMapper
    val responseBody = mapper.readTree(responseBodyAsString)
    val access_token = responseBody.get("access_token").textValue()
    val refresh_token = responseBody.get("refresh_token").textValue()
    TwitterTokens(access_token, refresh_token)
  }

  private def getParamsUrlEncoded(parameters: Map[String, String]): HttpRequest.BodyPublisher = {
    val urlEncoded = parameters
      .map { case (k, v) => k + "=" + URLEncoder.encode(v, StandardCharsets.UTF_8) }
      .mkString("&")
    HttpRequest.BodyPublishers.ofString(urlEncoded)
  }
}

package cl.gera.apitwitter2.auth

import cl.gera.apitwitter2.core.Properties
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.{FormBody, Headers, OkHttpClient, Request}

import java.nio.charset.StandardCharsets
import java.util.Base64

object TwitterOAuth2 {
  private val baseUrl = Properties.Twitter.baseUrl
  private val path = s"/oauth2"
  private val client = new OkHttpClient();

  def getRefreshToken(clientId: String, clientSecret: String, refreshToken: String): TwitterTokens = {
    val authorization: String = Base64.getEncoder.encodeToString(
      s"$clientId:$clientSecret".getBytes(StandardCharsets.UTF_8)
    )

    val formBody = new FormBody.Builder()
      .addEncoded("grant_type", "refresh_token")
      .addEncoded("client_id", clientId)
      .addEncoded("refresh_token", refreshToken)
      .build();

    val headers = new Headers.Builder()
      .add("ContentType", "application/x-www-form-urlencoded")
      .add("Authorization", s"Basic $authorization")
      .build()

    val request = new Request.Builder()
      .url(s"$baseUrl$path/token")
      .headers(headers)
      .post(formBody)
      .build();

    val response = client.newCall(request).execute()
    val statusCode = response.code()
    val responseBodyAsString = response.body().string()

    if (statusCode != 200)
      throw new RuntimeException(s"Unexpected status $statusCode in Twitter authentication.\n(response body: $responseBodyAsString)")

    // Parse response body
    val mapper = new ObjectMapper
    val responseBody = mapper.readTree(responseBodyAsString)
    val access_token = responseBody.get("access_token").textValue()
    val refresh_token = responseBody.get("refresh_token").textValue()
    TwitterTokens(access_token, refresh_token)
  }
}

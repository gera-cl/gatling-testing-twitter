package cl.gera.apitwitter2.auth

import cl.gera.apitwitter2.core.Properties
import java.net.URI
import java.net.http.HttpResponse.BodyHandlers
import java.net.http.{HttpClient, HttpRequest}

object GithubSecrets {
  private val baseUrl = Properties.Github.baseUrl
  private val repo = Properties.Github.repo
  private val path = s"/repos/$repo/actions"
  private val httpClient = HttpClient.newBuilder().build()

  def createOrModifySecret(secretName: String, secretValue: String, keyId: String): Unit = {
    val request = HttpRequest.newBuilder()
      .uri(new URI(s"$baseUrl$path/secrets/$secretName"))
      .headers(
        "Content-Type", "application/json",
        "Accept", "application/vnd.github+json",
        "Authorization", s"Bearer ${Properties.Github.token}"
      )
      .PUT(HttpRequest.BodyPublishers.ofString(s"""{"encrypted_value":"$secretValue","key_id":"$keyId"}"""))
      .build()
    val response = httpClient.send(request, BodyHandlers.ofString())
    val statusCode = response.statusCode()
    if (!Array(201, 204).contains(statusCode))
      throw new RuntimeException(s"Unexpected status $statusCode creating Github Secret")
  }
}

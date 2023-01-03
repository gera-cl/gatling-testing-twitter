package cl.gera.apitwitter2.auth

import cl.gera.apitwitter2.core.Properties
import okhttp3.{Headers, MediaType, OkHttpClient, Request, RequestBody}

object GithubSecrets {
  private val baseUrl = Properties.Github.baseUrl
  private val repo = Properties.Github.repo
  private val path = s"/repos/$repo/actions"
  private val client = new OkHttpClient();
  private val JSON = MediaType.get("application/json; charset=utf-8");

  def createOrModifySecret(secretName: String, secretValue: String, keyId: String): Unit = {
    val headers = new Headers.Builder()
      .add("Accept", "application/vnd.github+json")
      .add("Authorization", s"Bearer ${Properties.Github.token}")
      .build()

    val body = RequestBody.create(s"""{"encrypted_value":"$secretValue","key_id":"$keyId"}""", JSON);

    val request = new Request.Builder()
      .url(s"$baseUrl$path/secrets/$secretName")
      .headers(headers)
      .put(body)
      .build();

    val response = client.newCall(request).execute()
    val statusCode = response.code()
    val responseBodyAsString = response.body().string()

    if (!Array(201, 204).contains(statusCode))
      throw new RuntimeException(s"Unexpected status $statusCode creating Github Secret.\n(response body: $responseBodyAsString)")
  }
}

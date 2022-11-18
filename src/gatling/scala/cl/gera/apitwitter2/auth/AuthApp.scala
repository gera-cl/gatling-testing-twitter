package cl.gera.apitwitter2.auth

import cl.gera.apitwitter2.core.Properties

object AuthApp {
  def main(args: Array[String]): Unit = {
    // Load properties from environment variables
    val githubPublicKey = Properties.Github.repoPublicKey
    val githubPublicKeyId = Properties.Github.repoKeyId
    val twitterClientId = Properties.Twitter.client_id
    val twitterClientSecret = Properties.Twitter.client_secret
    val twitterAccessToken = Properties.Twitter.access_token
    val twitterRefreshToken = Properties.Twitter.refresh_token

    // Get a new access and refresh token
    val twitterTokens = TwitterOAuth2.getRefreshToken(twitterClientId.value, twitterClientSecret.value, twitterRefreshToken.value)

    // Save as repository secrets
    // Access token
    var encryptedSecret = SodiumHelper.encrypt(twitterTokens.access_token, githubPublicKey.value)
    GithubSecrets.createOrModifySecret(twitterAccessToken.name, encryptedSecret, githubPublicKeyId.value)
    // Refresh token
    encryptedSecret = SodiumHelper.encrypt(twitterTokens.refresh_token, githubPublicKey.value)
    GithubSecrets.createOrModifySecret(twitterRefreshToken.name, encryptedSecret, githubPublicKeyId.value)
  }
}

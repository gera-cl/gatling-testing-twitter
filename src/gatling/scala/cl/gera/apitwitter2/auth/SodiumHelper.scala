package cl.gera.apitwitter2.auth

import com.goterl.lazysodium.interfaces.Box
import com.goterl.lazysodium.{LazySodiumJava, SodiumJava}

import java.nio.charset.StandardCharsets
import java.util.Base64

object SodiumHelper {
  private val sodium = new SodiumJava()
  private val lazySodium = new LazySodiumJava(sodium, StandardCharsets.UTF_8)

  def encrypt(message: String, publicKey: String): String = {
    // Prepare data
    val bin_msg: Array[Byte] = message.getBytes()
    val bin_msg_length: Int = bin_msg.length
    val bin_key: Array[Byte] = Base64.getDecoder.decode(publicKey.getBytes(StandardCharsets.UTF_8))
    val cipher = new Array[Byte](Box.SEALBYTES + bin_msg_length)
    // Encrypt and then return a base64 string
    lazySodium.cryptoBoxSeal(cipher, bin_msg, bin_msg_length.toLong, bin_key)
    new String(Base64.getEncoder.encode(cipher), StandardCharsets.UTF_8)
  }
}

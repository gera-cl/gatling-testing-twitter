package cl.gera.apitwitter2.core

class Property(val name: String)  {
  val value: String = getEnv(name)

  override def toString: String = {
    value
  }

  private def getEnv(name: String): String = {
    val value = System.getenv(name)
    if (value == null)
      throw new RuntimeException("Environment variable \"%s\" not found.".format(name))
    value
  }
}

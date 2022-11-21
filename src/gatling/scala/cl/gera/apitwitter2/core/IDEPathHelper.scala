package cl.gera.apitwitter2.core

import java.nio.file.{Path, Paths}

object IDEPathHelper {
  private val projectRootDir = Paths.get(System.getProperty("user.dir"))
  private val gradleBuildDirectory = projectRootDir.resolve("build")
  private val gradleSrcDirectory = projectRootDir.resolve("src").resolve("gatling")

  val gradleSourcesDirectory: Path = gradleSrcDirectory.resolve("scala")
  val gradleResourcesDirectory: Path = gradleSrcDirectory.resolve("resources")
  val gradleBinariesDirectory: Path = gradleBuildDirectory.resolve("classes").resolve("scala").resolve("gatling")
  val resultsDirectory: Path = gradleBuildDirectory.resolve("reports").resolve("gatling")
  val recorderConfigFile: Path = gradleResourcesDirectory.resolve("recorder.conf")
}
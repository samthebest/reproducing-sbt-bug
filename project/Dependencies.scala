import sbt._

object Dependencies {
//  val scanamoVersion = "1.0.0-M15"

  object Misc {
//    val scanamo = "org.scanamo" %% "scanamo" % scanamoVersion
//    val caffeine = "com.github.ben-manes.caffeine" % "caffeine" % "2.9.3"
    val bigquery = "com.google.cloud" % "google-cloud-bigquery" % "2.16.1"
//    val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.7" % Test
//    val finagleHttp = "com.twitter" %% "finagle-http" % "22.7.0"
//    val bucket4jCore = "com.bucket4j" % "bucket4j-core" % "8.3.0"
    val unpickle = "com.lihaoyi" %% "upickle" % "1.6.0"
    val jaxbApi = "javax.xml.bind" % "jaxb-api" % "2.3.1"
    val opencsv = "com.opencsv" % "opencsv" % "3.6"
  }

  @deprecated("Don't provide all deps to all projects - figure out which ones need which")
  val theInternet = Seq(Misc.bigquery)
}

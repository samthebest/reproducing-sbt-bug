import sbt._

object Dependencies {
  object Misc {
    val bigquery = "com.google.cloud" % "google-cloud-bigquery" % "2.16.1"
  }

  @deprecated("Don't provide all deps to all projects - figure out which ones need which")
  val theInternet = Seq(Misc.bigquery)
}

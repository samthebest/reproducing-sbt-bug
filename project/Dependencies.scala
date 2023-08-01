import sbt._

object Dependencies {

  object AWS {
    val kinesisanalyticsRuntime: ModuleID = "com.amazonaws" % "aws-kinesisanalytics-runtime" % "1.2.0"
    val kinesis: ModuleID = "software.amazon.awssdk" % "kinesis" % "2.15.78"
    val sqs: ModuleID = "software.amazon.awssdk" % "sqs" % "2.15.78"
    // Think only used by MRP
    val s3: ModuleID = "software.amazon.awssdk" % "s3" % "2.15.78"
    val ssm: ModuleID = "com.amazonaws" % "aws-java-sdk-ssm" % "1.12.435"

    val all = Seq(kinesisanalyticsRuntime, kinesis, sqs, s3, ssm)
  }

  object TypeLevel {
    val catsCore: ModuleID = "org.typelevel" %% "cats-core" % "2.1.0"
    val squants: ModuleID = "org.typelevel" %% "squants" % "1.6.0"
  }

  val scanamoVersion = "1.0.0-M15"

  object TestKits {
    //  "org.scalatest" %% "scalatest" % "1.2.7" % Test,
    val scalaTest: ModuleID = "org.scalatest" %% "scalatest" % "3.2.10"
    val scanamoTestkit: ModuleID = "org.scanamo" %% "scanamo-testkit" % scanamoVersion
    val dockerScalatest: ModuleID = "com.whisk" %% "docker-testkit-scalatest" % "0.9.9"
    val dockerSpotify: ModuleID = "com.whisk" %% "docker-testkit-impl-spotify" % "0.9.9"
  }

  object Misc {
    val bigquery = "com.google.cloud" % "google-cloud-bigquery" % "2.16.1"
  }

  @deprecated("Don't provide all deps to all projects - figure out which ones need which")
  val theInternet = Seq(Misc.bigquery)
}

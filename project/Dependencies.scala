import sbt._

object Dependencies {
  object Flink {
    val flinkVersion = "1.13.2"

    // This doesn't seem to work - get "Error downloading"
//    val core: ModuleID = "org.apache.flink" %% "flink-core" % flinkVersion
    val clients: ModuleID = "org.apache.flink" %% "flink-clients" % flinkVersion
    val connectorKinesis: ModuleID = "org.apache.flink" %% "flink-connector-kinesis" % flinkVersion
    val scala: ModuleID = "org.apache.flink" %% "flink-scala" % flinkVersion
    val streamingScala: ModuleID = "org.apache.flink" %% "flink-streaming-scala" % flinkVersion
    val streamingJava: ModuleID = "org.apache.flink" %% "flink-streaming-java" % flinkVersion
    val testUtils: ModuleID = "org.apache.flink" %% "flink-test-utils" % flinkVersion
    val runtime: ModuleID = "org.apache.flink" %% "flink-runtime" % flinkVersion
    // Think only used by hv-flink-util
    val metricsDropwizard: ModuleID = "org.apache.flink" % "flink-metrics-dropwizard" % flinkVersion

    val all = Seq(clients, connectorKinesis, scala, streamingScala, streamingJava, testUtils % Test, runtime,
      metricsDropwizard,

      // Need this to make tests compile, Flink is super weird for putting these under a special classifier
      streamingScala classifier "tests",
      streamingJava classifier "tests"
    )
  }

  object Misc {
    val bigquery = "com.google.cloud" % "google-cloud-bigquery" % "2.16.1"
  }

  @deprecated("Don't provide all deps to all projects - figure out which ones need which")
  val theInternet = Seq(Misc.bigquery)
}

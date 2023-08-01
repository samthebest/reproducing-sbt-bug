import sbt._
import Dependencies._

lazy val commonSettings = Seq(
  organization := "hypervolt",
  scalaVersion := "2.12.16",
  // This doesn't do anything?! Must use `.jvmopts`
//  javaOptions ++= Seq(
//    "-Xmx8G",
//    "-Xss4M",
//    "-Xms2048M"
//  ),
  resolvers ++= Seq(
    "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/",
    Resolver.mavenLocal,
  ),
  CustomMergeStrat.mergeStrat,
  addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
)

lazy val root = (project in file("."))
  .aggregate(
//    core,
//    testKit,
//    conf,
//    common,
//    dynamoUtil,
    halogenTelemetryWriter,
  )
  .settings(
    commonSettings,
    name := "charger-session-metrics",
    publish / skip := true,
  )

//lazy val common = (project in file("common"))
////  .dependsOn(dynamoUtil)
//  .settings(
//    commonSettings,
//    libraryDependencies ++= theInternet,
//  )


val flinkVersion = "1.13.2"

lazy val halogenTelemetryWriter = (project in file("halogen-telemetry-writer"))
//  .dependsOn(core, dynamoUtil)
  .settings(
    commonSettings,
    libraryDependencies ++= theInternet,
    // TODO Check this - unsure this will work
    assembly / mainClass := Some("hypervolt.Job"),
  )

def utilProjectName(suffix: String): String = {
  s"hv-flink-util-$suffix"
}

lazy val core = (project in file("flink-util-projects/hv-flink-util"))
  .configs(IntegrationTest)
  .settings(
    name := "hv-flink-util",
    Defaults.itSettings,
    libraryDependencies ++= theInternet,
    commonSettings,
  )
//  .dependsOn(conf, testKit % "it,test")
  .dependsOn(testKit % "it,test")

lazy val testKit = (project in file("flink-util-projects/hv-flink-util-testkit"))
  .settings(
    name := utilProjectName("testkit"),
    commonSettings,
    libraryDependencies ++= Seq(
      Flink.scala,
      Flink.streamingScala,
      Flink.metricsDropwizard,
      Flink.testUtils,

      Misc.scanamo,
      Misc.caffeine,
      Misc.bigquery,

      AWS.s3,

      TestKits.dockerScalatest,
      TestKits.dockerSpotify,
      TestKits.scanamoTestkit,
      TestKits.scalaTest,
    ),
  )
////  .dependsOn(conf)

//lazy val conf = (project in file("flink-util-projects/hv-flink-util-conf"))
//  .settings(
//    name := utilProjectName("conf"),
//    commonSettings,
//    libraryDependencies += Flink.clients
//  )

//lazy val dynamoUtil = (project in file("hv-dynamo-util"))
//  .settings(
//    name := "hv-dynamo-util",
//    commonSettings,
//    libraryDependencies ++= Seq(
//      TypeLevel.squants,
//      TypeLevel.catsCore,
//      Misc.opencsv,
//      AWS.kinesis,
//      AWS.sqs,
//      Misc.unpickle,
//      TestKits.scalaTest % Test
//    )
//  )
////  .dependsOn(core, testKit)
//  .dependsOn(core)

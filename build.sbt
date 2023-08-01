import sbt._

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
  assembly / assemblyMergeStrategy := {
    case _ => MergeStrategy.first
  }
)

lazy val root = (project in file("."))
  .aggregate(
//    core,
//    testKit,
//    conf,
//    common,
//    dynamoUtil,
//    halogenTelemetryWriter,
  )
  .settings(
    commonSettings,
    name := "charger-session-metrics",
    publish / skip := true,
  )

def utilProjectName(suffix: String): String = {
  s"hv-flink-util-$suffix"
}

import Dependencies._

lazy val core = (project in file("flink-util-projects/hv-flink-util"))
  .settings(
    name := "hv-flink-util",
    libraryDependencies ++= theInternet,
    commonSettings,
  )
  .dependsOn(testKit)

lazy val testKit = (project in file("flink-util-projects/hv-flink-util-testkit"))
  .settings(
    name := utilProjectName("testkit"),
    commonSettings,
    libraryDependencies ++= Seq(Misc.bigquery),
  )
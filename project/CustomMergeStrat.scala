import sbt._
import sbtassembly.AssemblyKeys.{assembly, assemblyMergeStrategy}
import sbtassembly._

object CustomMergeStrat {
  lazy val mergeStrat =
    assembly / assemblyMergeStrategy := {
      case _ => MergeStrategy.first
    }
}

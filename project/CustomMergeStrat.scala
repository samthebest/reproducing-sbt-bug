import sbt._
import sbtassembly.AssemblyKeys.{assembly, assemblyMergeStrategy}
import sbtassembly._

object CustomMergeStrat {
  lazy val mergeStrat =
    assembly / assemblyMergeStrategy := {

      // SPECIALS
      // hocon files are fine to concatenate
      case PathList("reference.conf") => MergeStrategy.concat
      // Two implementations of the same interface can be loaded
      case PathList("META-INF", "services", _) => MergeStrategy.concat
      // take the flink connectors lib as newer (TODO(bme):CHECK REGULARLY)
      case PathList("META-INF", "native", "libnetty_transport_native_epoll_x86_64.so") =>
        MergeStrategy.last

      // CONCATS
      // copyright notices
      case PathList("NOTICE") => MergeStrategy.concat
      // licences
      case PathList("LICENSE") => MergeStrategy.concat
      case PathList("LICENSE.txt") => MergeStrategy.concat

      // All META-INFs from here are either `discard` or `last`, and seem unimportant
      case PathList("META-INF", _*) => MergeStrategy.discard


      // DISCARDS
      // jar description produced by scala libs
      case PathList("rootdoc.txt") => MergeStrategy.discard
      // The represent module linkage, there is no merge possible.
      case PathList("module-info.class") => MergeStrategy.discard
      // we aren't native
      case PathList(ps @ _*)
        if ps.last == "native-image.properties" || ps.last == "reflection-config.json" =>
        MergeStrategy.discard
      // delete aws codegen json
      case PathList("codegen-resources", p @ _) if p.endsWith(".json") =>
        MergeStrategy.discard
      // delete aws codegen json
      case PathList("codegen-resources", "customization.config") =>
        MergeStrategy.discard
      case PathList("codegen-resources", p@_) if p.endsWith(".json") =>
        MergeStrategy.discard
      case PathList("META-INF", "versions", _, "module-info.class") => MergeStrategy.discard
      case PathList("arrow-git.properties")                                => MergeStrategy.discard
      // un-sign jars
      case PathList(p@_*)
        if p.last.toLowerCase.endsWith(".dsa") || p.last.toLowerCase
          .endsWith(".sf") || p.last.toLowerCase
          .endsWith(".rsa") =>
        MergeStrategy.discard
      case x if x.contains("native-image/io.netty/codec-http/native-image.properties") =>
        MergeStrategy.discard


      // FILTERS
      // this should probably either
      // 1. discard both and get a more up-to-date copy from mozilla.
      // 2. custom merge that respects the format.
      case PathList("mozilla", "public-suffix-list.txt") =>
        MergeStrategy.filterDistinctLines
      // as above
      case PathList("mime.types") => MergeStrategy.filterDistinctLines

      // LASTS
      // This keeps our aws sdk version vs the shaded one from flink
      case PathList("VersionInfo.java") => MergeStrategy.last
      case PathList("org", "apache", "log4j", _*) => MergeStrategy.last
      case PathList("org", "slf4j", _*) => MergeStrategy.last
      case PathList("javax", "xml", "bind", _*) => MergeStrategy.last
      case PathList("javax", "ws", "rs", _*) => MergeStrategy.last
      // Added to deal with testKit
      case PathList("javax", "inject", _*) => MergeStrategy.last
      case PathList("log4j2-test.properties") => MergeStrategy.last


      // FIRSTS
      case x if x.contains("scala/annotation/nowarn.class") => MergeStrategy.first
      case x if x.contains("scala/annotation/nowarn$.class") => MergeStrategy.first
      case x if x.endsWith("netty-tcnative-boringssl-static/pom.properties") => MergeStrategy.first
      case x if x.endsWith("netty-tcnative-boringssl-static/pom.xml") => MergeStrategy.first
      case x if x.contains("libnetty_transport_native_epoll_aarch_64.so") => MergeStrategy.first
      case x if x.endsWith("com.fasterxml.jackson.core.JsonFactory") => MergeStrategy.first
      case x if x.endsWith("reactor.blockhound.integration.BlockHoundIntegration") => MergeStrategy.first

      case _ => MergeStrategy.deduplicate
    }
}

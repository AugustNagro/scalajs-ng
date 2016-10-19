addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.12")
dependencyOverrides += "org.scala-js" % "sbt-scalajs" % "0.6.12"

resolvers += Resolver.sonatypeRepo("snapshots")
addSbtPlugin("be.doeraene" % "sbt-scalajs-reflection" % "0.1.1")
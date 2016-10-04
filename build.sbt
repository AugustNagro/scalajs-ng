lazy val `scalajs-ng` = (project in file("."))
  .enablePlugins(ScalaJSPlugin, ScalaJSReflectionPlugin)
  .aggregate(plugin)
  .settings(commonSettings: _*)
  .settings(publishingSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scalameta" %% "scalameta" % "1.1.0",
      "be.doeraene" %%% "scalajs-reflection" % "0.2.0-SNAPSHOT",
      "com.github.lukajcb" %%% "rxscala-js" % "0.4.0",
      "com.lihaoyi" %%% "scalatags" % "0.6.0"
    ),
    addCompilerPlugin(
      "org.scalameta" % "paradise" % "3.0.0-SNAPSHOT" cross CrossVersion.full),
    scalaJSReflectSelectors ++= Seq(
      selectDescendentClasses("ng.macros.NGAnnotation") -> reflectEnumerateClass()
    )
  )

lazy val plugin = project
  .settings(commonSettings: _*)
  .settings(publishingSettings: _*)
  .settings(
    name := "sbt-scalajs-ng",
    description := "sbt plugin for scalajs-ng",
    sbtPlugin := true,
    scalaVersion := "2.10.5",
    addSbtPlugin("be.doeraene" % "sbt-scalajs-reflection" % "0.2.0-SNAPSHOT")
  )

lazy val commonSettings = Seq(
  organization := "com.augustnagro",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation",
                        "-unchecked",
                        "-feature",
                        "-language:implicitConversions",
                        "-Xlint"),
  resolvers += Resolver.sonatypeRepo("releases"),
  resolvers += Resolver.sonatypeRepo("snapshots")
)

lazy val publishingSettings = Seq(
  homepage := Some(url("https://github.com/augustnagro/scalajs-ng")),
  licenses += ("MIT", url(
    "http://www.opensource.org/licenses/mit-license.php")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/augustnagro/scalajs-ng"),
      "scm:git:git@github.com:augustnagro/scalajs-ng.git",
      Some("scm:git:git@github.com:augustnagro/scalajs-ng.git")
    )),
  developers := List(Developer(id = "augustnagro@gmail.com",
                          name = "August Nagro",
                          email = "augustnagro@gmail.com",
                          url = url("https://augustnagro.com"))),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "services/local/staging/deploy/maven2")
  }
)

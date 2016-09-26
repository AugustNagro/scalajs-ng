package com.august.scalajsng.sbtplugin

import be.doeraene.sjsreflect.sbtplugin.ScalaJSReflectionPlugin
import be.doeraene.sjsreflect.sbtplugin.ScalaJSReflectionPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin
import sbt.{CrossVersion, _}

object NGPlugin extends AutoPlugin {

  override def requires: Plugins = ScalaJSPlugin && ScalaJSReflectionPlugin

  override def projectSettings = Seq(
    addCompilerPlugin(
      "org.scalameta" % "paradise" % "3.0.0-SNAPSHOT" cross CrossVersion.full),

    scalaJSReflectSelectors ++= Seq(
      selectDescendentClasses("ng.macros.NGAnnotation") -> reflectClassByName()
    )
  )
}


lazy val root = (project in file("."))
  .settings(
    name := "ComposeKey.alfredsnippets generator",
    organization := "nl.jqno.composekey",
    version := "0.0.1-SNAPSHOT",

    scalaVersion := "2.12.4",
    scalacOptions ++= Seq(
      "-Xfatal-warnings", "-encoding", "utf-8", "-explaintypes",
      "-deprecation", "-feature", "-unchecked",
      "-Xlint:doc-detached", "-Xlint:missing-interpolator", "-Xlint:nullary-override", "-Xlint:nullary-unit", "-Xlint:type-parameter-shadow", 
      "-Yno-adapted-args", "-Ywarn-dead-code", "-Ywarn-extra-implicit", "-Ywarn-inaccessible",
      "-Ywarn-unused:implicits", "-Ywarn-unused:imports", "-Ywarn-unused:locals", "-Ywarn-unused:params", "-Ywarn-unused:patvars", "-Ywarn-unused:privates"
    ),

    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.6.7"
    )
  )


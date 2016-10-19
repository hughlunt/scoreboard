name := """scoreboard"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(sharedSettings)
  .settings(libraryDependencies ++= Seq (
  "org.webjars" % "requirejs" % "2.1.14-1",
  "org.webjars" % "underscorejs" % "1.6.0-3",
  "org.webjars" % "jquery" % "1.11.1",
  "org.webjars" % "bootstrap" % "3.3.6" exclude("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.4.9" exclude("org.webjars", "jquery")
  ))

lazy val sharedSettings = Seq(
  scalaVersion := "2.11.7",
  scalacOptions := Seq("-feature", "-unchecked", "-deprecation"),
  libraryDependencies ++= List(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "org.postgresql" % "postgresql" % "9.4.1209",
  "com.github.tminglei" %% "slick-pg" % "0.14.1",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.14.1",
  "com.github.tminglei" %% "slick-pg_joda-time" % "0.14.1",
  "com.github.tminglei" %% "slick-pg_jts" % "0.14.1"
  )
)

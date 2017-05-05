enablePlugins(ScalaJSPlugin)

name := "bg-stats"

scalaVersion := "2.11.11"

scalacOptions := Seq(
  "-Xlint",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-encoding", "utf8",
  "-Xfatal-warnings")

libraryDependencies ++= Seq(
  "io.monix" %%% "monix" % Version.monix,
  "org.scalaz" %%% "scalaz-core" % Version.scalaz,
  "com.github.japgolly.scalajs-react" %%% "core" % Version.scalaJsReact)

jsDependencies ++= Seq(
  "org.webjars.bower" % "react" % Version.react
   /        "react-with-addons.js"
   minified "react-with-addons.min.js"
   commonJSName "React",
  "org.webjars.bower" % "react" % Version.react
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM")

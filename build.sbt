enablePlugins(ScalaJSPlugin)

name := "bg-stats"

scalaVersion := "2.12.4"

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

scalaJSUseMainModuleInitializer := true

skip in packageJSDependencies := false

jsDependencies ++= Seq(
  "org.webjars.npm" % "react" % "16.2.0"
    /        "umd/react.development.js"
    minified "umd/react.production.min.js"
    commonJSName "React",
  "org.webjars.npm" % "react-dom" % "16.2.0"
    /         "umd/react-dom.development.js"
    minified  "umd/react-dom.production.min.js"
    dependsOn "umd/react.development.js"
    commonJSName "ReactDOM",
  "org.webjars.npm" % "react-dom" % "16.2.0"
    /         "umd/react-dom-server.browser.development.js"
    minified  "umd/react-dom-server.browser.production.min.js"
    dependsOn "umd/react-dom.development.js"
    commonJSName "ReactDOMServer")

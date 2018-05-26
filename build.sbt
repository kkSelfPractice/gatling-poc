
name := "selfPrac"

version := "1.0"

scalaVersion := "2.11.7"

enablePlugins(GatlingPlugin)

libraryDependencies ++= Seq(
  "io.gatling" % "gatling-test-framework" % "2.2.5" % "test",
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.5" % "test"
)

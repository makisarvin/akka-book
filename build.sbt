name := "Akka Book"

organization := "zzz.akka"

version := "0.1"

scalaVersion := "2.10.3"

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.1.2"
)


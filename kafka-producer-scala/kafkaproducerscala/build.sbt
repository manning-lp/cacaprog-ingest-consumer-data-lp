val scala3Version = "3.5.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "KafkaProducerScala",
    version := "0.1.0-SNAPSHOT",
    
    scalaVersion := scala3Version,

    // Use Kafka clients library, which is not dependent on Scala version
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.7.1",

    // Add testing libraries
    libraryDependencies ++= Seq(
      // MUnit for Scala 3 testing
      "org.scalameta" %% "munit" % "1.0.0" % Test,

      // ScalaTest for Scala 3 testing
      "org.scalatest" %% "scalatest" % "3.2.15" % Test
    )
  )

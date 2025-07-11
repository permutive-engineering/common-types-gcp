import sbt._
import sbt.Keys._

object Dependencies {

  lazy val `common-types-gcp` = Seq(
    "org.typelevel" %% "cats-core" % "2.13.0"
  ) ++ Seq(
    "org.typelevel" %% "munit-cats-effect"   % "2.0.0",
    "org.http4s"    %% "http4s-dsl"          % "0.23.30",
    "org.http4s"    %% "http4s-ember-server" % "0.23.30",
    "org.slf4j"      % "slf4j-nop"           % "2.0.17"
  ).map(_ % Test)

  lazy val `common-types-gcp-http4s` = Seq(
    "org.http4s" %% "http4s-core" % "0.23.30"
  )

  lazy val `common-types-gcp-circe` = Seq(
    "io.circe" %% "circe-core" % "0.14.14"
  )

  lazy val `common-types-gcp-pureconfig` = Seq(
    "com.github.pureconfig" %% "pureconfig-core" % "0.17.9"
  )

  lazy val `common-types-gcp-tapir` = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.11.36"
  )

  lazy val `common-types-gcp-scalacheck` = Seq(
    "org.scalacheck" %% "scalacheck" % "1.18.1"
  ) ++ Seq(
    "org.scalameta" %% "munit-scalacheck" % "1.1.0" % Test
  )

}

import sbt._
import sbt.Keys._

object Dependencies {

  lazy val `common-types-gcp` = Seq(
    "org.typelevel" %% "cats-core" % "2.10.0"
  ) ++ Seq(
    "org.typelevel" %% "munit-cats-effect"   % "2.0.0-M4",
    "org.http4s"    %% "http4s-dsl"          % "0.23.27",
    "org.http4s"    %% "http4s-ember-server" % "0.23.27",
    "org.slf4j"      % "slf4j-nop"           % "2.0.12"
  ).map(_ % Test)

  lazy val `common-types-gcp-http4s` = Seq(
    "org.http4s" %% "http4s-core" % "0.23.27"
  )

  lazy val `common-types-gcp-circe` = Seq(
    "io.circe" %% "circe-core" % "0.14.7"
  )

  lazy val `common-types-gcp-pureconfig` = Seq(
    "com.github.pureconfig" %% "pureconfig-core" % "0.17.6"
  )

  lazy val `common-types-gcp-scalacheck` = Seq(
    "org.scalacheck" %% "scalacheck" % "1.17.0"
  ) ++ Seq(
    "org.scalameta" %% "munit-scalacheck" % "0.7.29" % Test
  )

}

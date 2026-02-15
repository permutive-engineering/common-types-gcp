ThisBuild / scalaVersion           := "2.13.18"
ThisBuild / crossScalaVersions     := Seq("2.13.18", "3.3.7")
ThisBuild / organization           := "com.permutive"
ThisBuild / versionPolicyIntention := Compatibility.BinaryAndSourceCompatible

addCommandAlias("ci-test", "fix --check; versionPolicyCheck; mdoc; publishLocal; +test")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "versionCheck; github; ci-release")

lazy val documentation = project
  .enablePlugins(MdocPlugin)
  .dependsOn(allModules)

lazy val `common-types-gcp` = module
  .settings(libraryDependencies += scalaVersion.value.on(2)("org.scala-lang" % "scala-reflect" % scalaVersion.value))
  .settings(libraryDependencies ++= Dependencies.`common-types-gcp`)
  .settings(Test / fork := true)

lazy val `common-types-gcp-circe` = module
  .settings(libraryDependencies ++= Dependencies.`common-types-gcp-circe`)
  .dependsOn(`common-types-gcp`)

lazy val `common-types-gcp-http4s` = module
  .settings(libraryDependencies ++= Dependencies.`common-types-gcp-http4s`)
  .dependsOn(`common-types-gcp`)

lazy val `common-types-gcp-pureconfig` = module
  .settings(libraryDependencies ++= Dependencies.`common-types-gcp-pureconfig`)
  .dependsOn(`common-types-gcp`)

lazy val `common-types-gcp-tapir` = module
  .settings(libraryDependencies ++= Dependencies.`common-types-gcp-tapir`)
  .dependsOn(`common-types-gcp`)

lazy val `common-types-gcp-scalacheck` = module
  .settings(libraryDependencies ++= Dependencies.`common-types-gcp-scalacheck`)
  .dependsOn(`common-types-gcp`)

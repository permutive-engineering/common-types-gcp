# `common-types-gcp`

GPC common types that can be used on any Scala services or libraries.

The list of common types are:

- [`DatasetName`](modules/common-types-gcp/src/main/scala/com/permutive/common/types/gcp/DatasetName.scala)
- [`DatasetMultiRegion`](modules/common-types-gcp/src/main/scala/com/permutive/common/types/gcp/DatasetMultiRegion.scala)
- [`ProjectId`](modules/common-types-gcp/src/main/scala/com/permutive/common/types/gcp/ProjectId.scala)

---

- [Installation](#installation)
- [Usage](#usage)
- [Integrations](#integrations)
  - [`common-types-gcp-circe`](#common-types-gcp-circe)
    - [Installation](#installation)
    - [Usage](#usage)
  - [`common-types-gcp-http4s`](#common-types-gcp-http4s)
    - [Installation](#installation)
    - [Usage](#usage)
  - [`common-types-gcp-pureconfig`](#common-types-gcp-pureconfig)
    - [Installation](#installation)
    - [Usage](#usage)
  - [`common-types-gcp-tapir`](#common-types-gcp-tapir)
    - [Installation](#installation)
    - [Usage](#usage)
  - [`common-types-gcp-scalacheck`](#common-types-gcp-scalacheck)
    - [Installation](#installation)
    - [Usage](#usage)
- [Contributors to this project](#contributors-to-this-project)

## Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp" % "1.2.1"
```

## Usage

Every type includes implicit instances for cats' `Eq`, `Show`, `Order`, `Hash`.

In addition, types like `ProjectId` also contain smart constructors:

**Obtaining the current `ProjectId` from Google's metadata service:**

```scala
import cats.Eval

val projectId: Eval[ProjectId] = ProjectId.unsafeFromGCP()
```

> 👆🏼 This method will only return a valid project ID if run inside a workload.

**Creating a random value:**

```scala
val projectId: ProjectId = ProjectId.random()
```

> 👆🏼 It will raise a warning if used outside `Test` or `IntegrationTest` configurations

**Creating a value from a string (returning `Either`):**


```scala
val projectId: Either[String, ProjectId]=
  ProjectId.fromString("test-project")
```

**Effectful alternative:**


```scala
val projectId: IO[ProjectId]=
  ProjectId.fromStringF[IO]("test-project")
```

**Creating a value from a literal string:**


```scala
val projectId: ProjectId = ProjectId("test-project")
```

> 👆🏼 It will fail at compile time if not a project ID or not using a literal.

## Integrations

### `common-types-gcp-circe`

#### Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp-circe" % "1.2.1"
```

#### Usage

Just add the following import:

```scala
import com.permutive.common.types.gcp.circe._
```

It will bring `Encoder`, `Decoder`, `KeyEncoder` & `KeyDecoder` instances for the
available types into scope.

### `common-types-gcp-http4s`

#### Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp-http4s" % "1.2.1"
```

#### Usage

Just add the following import:

```scala
import com.permutive.common.types.gcp.http4s._
```

It will bring `SegmentEncoder`, `QueryParamEncoder` & `QueryParamDecoder`
instances for the available types into scope.

### `common-types-gcp-pureconfig`

#### Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp-pureconfig" % "1.2.1"
```

#### Usage

Just add the following import:

```scala
import com.permutive.common.types.gcp.pureconfig._
```

It will bring `ConfigReader` & `ConfigWriter` instances for the available types
into scope.

For the case of `ProjectId` you can use the special value `gcp` on your
`application.conf` when running inside a workload and it will retrieve the
current `ProjectId` from Google's metadata service.

### `common-types-gcp-tapir`

#### Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp-tapir" % "1.2.1"
```

#### Usage

Just add the following import:

```scala
import com.permutive.common.types.gcp.tapir._
```

It will bring `Codec`/`Schema` instances for the available types, as well as adding a new extension method (`example`) to common types' companion objects that can be used to add an example value to tapir endpoints:

```scala
import com.permutive.common.types.gcp.ProjectId
import com.permutive.common.types.gcp.tapir._

import sttp.tapir._

endpoint.get
  .in("projects")
  .in(path[ProjectId]("project_id").example(ProjectId.example))
// res12: Endpoint[Unit, typelevel.LowPriorityTupleConcat0.<refinement>.this.type.Out, Unit, Unit, Any] = Endpoint(
//   securityInput = Empty(
//     codec = sttp.tapir.Codec$$anon$4@5e12767b,
//     info = Info(
//       description = None,
//       examples = List(),
//       deprecated = false,
//       attributes = AttributeMap(storage = Map())
//     )
//   ),
//   input = Pair(
//     left = Pair(
//       left = Pair(
//         left = Empty(
//           codec = sttp.tapir.Codec$$anon$4@5e12767b,
//           info = Info(
//             description = None,
//             examples = List(),
//             deprecated = false,
//             attributes = AttributeMap(storage = Map())
//           )
//         ),
//         right = FixedMethod(
//           m = Method(method = "GET"),
//           codec = sttp.tapir.Codec$$anon$4@4c79b5bd,
//           info = Info(
//             description = None,
//             examples = List(),
//             deprecated = false,
//             attributes = AttributeMap(storage = Map())
//           )
//         ),
//         combine = sttp.tapir.internal.package$$$Lambda$14576/0x00007fcfaa923a30@3940b5ff,
//         split = sttp.tapir.internal.package$$$Lambda$14577/0x00007fcfaa923fd8@16439897
//       ),
//       right = FixedPath(
//         s = "projects",
//         codec = sttp.tapir.Codec$$anon$4@4b34ab92,
//         info = Info(
//           description = None,
//           examples = List(),
//           deprecated = false,
//           attributes = AttributeMap(storage = Map())
//         )
//       ),
//       combine = sttp.tapir.internal.package$$$Lambda$14578/0x00007fcfaa924b18@4a613b14,
// ...
```

### `common-types-gcp-scalacheck`

#### Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp-scalacheck" % "1.2.1"
```

#### Usage

Just add the following import:

```scala
import com.permutive.common.types.gcp.scalacheck._
```

It will bring `Arbitrary` instances for the available types into scope.

## Contributors to this project

| <a href="https://github.com/alejandrohdezma"><img alt="alejandrohdezma" src="https://avatars.githubusercontent.com/u/9027541?v=4&s=120" width="120px" /></a> |
| :--: |
| <a href="https://github.com/alejandrohdezma"><sub><b>alejandrohdezma</b></sub></a> |
# `common-types-gcp`

GPC common types that can be used on any Scala services or libraries.

The list of common types are:

- [`DatasetName`](modules/common-types-gcp/src/main/scala/com/permutive/common/types/gcp/DatasetName.scala)
- [`DatasetMultiRegion`](modules/common-types-gcp/src/main/scala/com/permutive/common/types/gcp/DatasetMultiRegion.scala)
- [`ProjectId`](modules/common-types-gcp/src/main/scala/com/permutive/common/types/gcp/ProjectId.scala)

```scala mdoc:toc
```

## Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp" % "@VERSION@"
```

## Usage

Every type includes implicit instances for cats' `Eq`, `Show`, `Order`, `Hash`.

In addition, types like `ProjectId` also contain smart constructors:

**Obtaining the current `ProjectId` from Google's metadata service:**

```scala mdoc:silent:warn
import cats.Eval

val projectId: Eval[ProjectId] = ProjectId.unsafeFromGCP()
```

> 👆🏼 This method will only return a valid project ID if run inside a workload.

**Creating a random value:**

```scala mdoc:silent:warn
val projectId: ProjectId = ProjectId.random()
```

> 👆🏼 It will raise a warning if used outside `Test` or `IntegrationTest` configurations

**Creating a value from a string (returning `Either`):**

```scala mdoc:reset:invisible
import com.permutive.common.types.gcp._
```

```scala mdoc:silent
val projectId: Either[String, ProjectId]=
  ProjectId.fromString("test-project")
```

**Effectful alternative:**

```scala mdoc:reset:invisible
import cats.effect.IO
import com.permutive.common.types.gcp._
```

```scala mdoc:silent
val projectId: IO[ProjectId]=
  ProjectId.fromStringF[IO]("test-project")
```

**Creating a value from a literal string:**

```scala mdoc:reset:invisible
import com.permutive.common.types.gcp._
```

```scala mdoc:silent
val projectId: ProjectId = ProjectId("test-project")
```

> 👆🏼 It will fail at compile time if not a project ID or not using a literal.

## Integrations

### `common-types-gcp-circe`

#### Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp-circe" % "@VERSION@"
```

#### Usage

Just add the following import:

```scala mdoc:reset:silent:warn
import com.permutive.common.types.gcp.circe._
```

It will bring `Encoder`, `Decoder`, `KeyEncoder` & `KeyDecoder` instances for the
available types into scope.

### `common-types-gcp-http4s`

#### Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp-http4s" % "@VERSION@"
```

#### Usage

Just add the following import:

```scala mdoc:reset:silent:warn
import com.permutive.common.types.gcp.http4s._
```

It will bring `SegmentEncoder`, `QueryParamEncoder` & `QueryParamDecoder`
instances for the available types into scope.

### `common-types-gcp-pureconfig`

#### Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp-pureconfig" % "@VERSION@"
```

#### Usage

Just add the following import:

```scala mdoc:reset:silent:warn
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
"com.permutive" %% "common-types-gcp-tapir" % "@VERSION@"
```

#### Usage

Just add the following import:

```scala mdoc:silent:warn
import com.permutive.common.types.gcp.tapir._
```

It will bring `Codec`/`Schema` instances for the available types, as well as adding a new extension method (`example`) to common types' companion objects that can be used to add an example value to tapir endpoints:

```scala mdoc
import com.permutive.common.types.gcp.ProjectId
import com.permutive.common.types.gcp.tapir._

import sttp.tapir._

endpoint.get
  .in("projects")
  .in(path[ProjectId]("project_id").example(ProjectId.example))
```

### `common-types-gcp-scalacheck`

#### Installation

Add the following dependency to your project:

```sbt
"com.permutive" %% "common-types-gcp-scalacheck" % "@VERSION@"
```

#### Usage

Just add the following import:

```scala mdoc:reset:silent:warn
import com.permutive.common.types.gcp.scalacheck._
```

It will bring `Arbitrary` instances for the available types into scope.

## Contributors to this project

@CONTRIBUTORS_TABLE@
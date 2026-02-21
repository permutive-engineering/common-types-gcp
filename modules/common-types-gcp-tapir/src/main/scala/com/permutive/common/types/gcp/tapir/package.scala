/*
 * Copyright 2024-2026 Permutive Ltd. <https://permutive.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.permutive.common.types.gcp

import cats.syntax.all._

import sttp.tapir.Codec
import sttp.tapir.CodecFormat
import sttp.tapir.Schema

package object tapir {

  implicit class DatasetMultiRegionOps(val t: DatasetMultiRegion.type) {

    val example: DatasetMultiRegion = t.US

  }

  implicit val DatasetMultiRegionCodec: Codec[String, DatasetMultiRegion, CodecFormat.TextPlain] =
    Codec.string.mapEither(DatasetMultiRegion.fromString(_))(_.show)

  implicit val DatasetMultiRegionSchema: Schema[DatasetMultiRegion] = Schema
    .string[DatasetMultiRegion]
    .description("Google Cloud BigQuery Dataset Multi-Region")
    .encodedExample(DatasetMultiRegion.example.value)

  implicit def DatasetMultiRegionMapSchema[V: Schema]: Schema[Map[DatasetMultiRegion, V]] = Schema.schemaForMap(_.value)

  implicit class DatasetNameOps(val t: DatasetName.type) {

    val example: DatasetName = t.apply("example_dataset")

  }

  implicit val DatasetNameCodec: Codec[String, DatasetName, CodecFormat.TextPlain] =
    Codec.string.map(DatasetName(_))(_.show)

  implicit val DatasetNameSchema: Schema[DatasetName] = Schema
    .string[DatasetName]
    .description("Google Cloud BigQuery Dataset Name")
    .encodedExample(DatasetName.example.value)

  implicit def DatasetNameMapSchema[V: Schema]: Schema[Map[DatasetName, V]] = Schema.schemaForMap(_.value)

  implicit class ProjectIdOps(val t: ProjectId.type) {

    val example: ProjectId = t.apply("example-project")

  }

  implicit val ProjectIdCodec: Codec[String, ProjectId, CodecFormat.TextPlain] =
    Codec.string.mapEither(ProjectId.fromString(_))(_.show)

  implicit val ProjectIdSchema: Schema[ProjectId] = Schema
    .string[ProjectId]
    .description("Google Cloud Project ID")
    .encodedExample(ProjectId.example.value)

  implicit def ProjectIdMapSchema[V: Schema]: Schema[Map[ProjectId, V]] = Schema.schemaForMap(_.value)

}

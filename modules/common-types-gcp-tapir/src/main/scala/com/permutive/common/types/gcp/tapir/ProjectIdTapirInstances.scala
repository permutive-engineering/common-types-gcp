/*
 * Copyright 2024 Permutive Ltd. <https://permutive.com>
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

package com.permutive.common.types.gcp.tapir

import cats.syntax.all._

import com.permutive.common.types.gcp.ProjectId
import sttp.tapir.Codec
import sttp.tapir.CodecFormat
import sttp.tapir.Schema

trait ProjectIdTapirInstances {

  implicit class ProjectIdOps(val t: ProjectId.type) {

    val example: ProjectId = t.apply("example-project")

  }

  implicit def ProjectIdCodec: Codec[String, ProjectId, CodecFormat.TextPlain] =
    Codec.string.mapEither(ProjectId.fromString(_))(_.show)

  implicit def ProjectIdSchema: Schema[ProjectId] = Schema
    .string[ProjectId]
    .description("Google Cloud Project ID")
    .encodedExample(ProjectId.example.value)

  implicit def ProjectIdMapSchema[V: Schema]: Schema[Map[ProjectId, V]] = Schema.schemaForMap(_.value)

}

object ProjectIdTapirInstances extends ProjectIdTapirInstances

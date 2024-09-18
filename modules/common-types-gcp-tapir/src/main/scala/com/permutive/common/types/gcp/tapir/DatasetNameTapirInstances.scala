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

import com.permutive.common.types.gcp.DatasetName
import sttp.tapir.Codec
import sttp.tapir.CodecFormat
import sttp.tapir.Schema

trait DatasetNameTapirInstances {

  implicit class DatasetNameOps(val t: DatasetName.type) {

    val example: DatasetName = t.apply("example_dataset")

  }

  implicit def DatasetNameCodec: Codec[String, DatasetName, CodecFormat.TextPlain] =
    Codec.string.map(DatasetName(_))(_.show)

  implicit def DatasetNameSchema: Schema[DatasetName] = Schema
    .string[DatasetName]
    .description("Google Cloud BigQuery Dataset Name")
    .encodedExample(DatasetName.example.value)

  implicit def DatasetNameMapSchema[V: Schema]: Schema[Map[DatasetName, V]] = Schema.schemaForMap(_.value)

}

object DatasetNameTapirInstances extends DatasetNameTapirInstances

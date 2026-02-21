/*
 * Copyright 2024-2025 Permutive Ltd. <https://permutive.com>
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

import io.circe.Decoder
import io.circe.Encoder
import io.circe.KeyDecoder
import io.circe.KeyEncoder

package object circe {

  implicit val DatasetMultiRegionEncoder: Encoder[DatasetMultiRegion] = Encoder[String].contramap(_.value)

  implicit val DatasetMultiRegionDecoder: Decoder[DatasetMultiRegion] =
    Decoder[String].emap(DatasetMultiRegion.fromString)

  implicit val DatasetMultiRegionKeyEncoder: KeyEncoder[DatasetMultiRegion] = _.value

  implicit val DatasetMultiRegionKeyDecoder: KeyDecoder[DatasetMultiRegion] = DatasetMultiRegion.fromString(_).toOption

  implicit val DatasetNameEncoder: Encoder[DatasetName] = Encoder[String].contramap(_.value)

  implicit val DatasetNameDecoder: Decoder[DatasetName] = Decoder[String].map(DatasetName(_))

  implicit val DatasetNameKeyEncoder: KeyEncoder[DatasetName] = _.value

  implicit val DatasetNameKeyDecoder: KeyDecoder[DatasetName] = DatasetName(_).some

  implicit val ProjectIdEncoder: Encoder[ProjectId] = Encoder[String].contramap(_.value)

  implicit val ProjectIdDecoder: Decoder[ProjectId] = Decoder[String].emap(ProjectId.fromString)

  implicit val ProjectIdKeyEncoder: KeyEncoder[ProjectId] = _.value

  implicit val ProjectIdKeyDecoder: KeyDecoder[ProjectId] = ProjectId.fromString(_).toOption

}

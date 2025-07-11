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

package com.permutive.common.types.gcp.circe

import com.permutive.common.types.gcp.DatasetMultiRegion
import io.circe.Decoder
import io.circe.Encoder
import io.circe.KeyDecoder
import io.circe.KeyEncoder

trait DatasetMultiRegionCirceInstances {

  implicit def DatasetMultiRegionEncoder: Encoder[DatasetMultiRegion] = Encoder[String].contramap(_.value)

  implicit def DatasetMultiRegionDecoder: Decoder[DatasetMultiRegion] =
    Decoder[String].emap(DatasetMultiRegion.fromString)

  implicit def DatasetMultiRegionKeyEncoder: KeyEncoder[DatasetMultiRegion] = _.value

  implicit def DatasetMultiRegionKeyDecoder: KeyDecoder[DatasetMultiRegion] = DatasetMultiRegion.fromString(_).toOption

}

object DatasetMultiRegionCirceInstances extends DatasetMultiRegionCirceInstances

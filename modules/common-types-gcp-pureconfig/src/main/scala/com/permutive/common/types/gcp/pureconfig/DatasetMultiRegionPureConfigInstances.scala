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

package com.permutive.common.types.gcp.pureconfig

import cats.syntax.all._

import com.permutive.common.types.gcp.DatasetMultiRegion
import pureconfig.ConfigReader
import pureconfig.ConfigWriter
import pureconfig.error.CannotConvert
import pureconfig.error.FailureReason

trait DatasetMultiRegionPureConfigInstances {

  sealed abstract class UnableToRetrieveDatasetMultiRegion(override val description: String) extends FailureReason

  implicit def DatasetMultiRegionConfigReader: ConfigReader[DatasetMultiRegion] = ConfigReader[String].emap { string =>
    DatasetMultiRegion.fromString(string).leftMap(CannotConvert(string, "DatasetMultiRegion", _))
  }

  implicit def DatasetMultiRegionConfigWriter: ConfigWriter[DatasetMultiRegion] =
    ConfigWriter[String].contramap(_.value)

}

object DatasetMultiRegionPureConfigInstances extends DatasetMultiRegionPureConfigInstances

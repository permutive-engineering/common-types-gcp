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

import com.permutive.common.types.gcp.DatasetName
import pureconfig.ConfigReader
import pureconfig.ConfigWriter

trait DatasetNamePureConfigInstances {

  implicit def DatasetNameConfigReader: ConfigReader[DatasetName] = ConfigReader[String].map(DatasetName(_))

  implicit def DatasetNameConfigWriter: ConfigWriter[DatasetName] = ConfigWriter[String].contramap(_.value)

}

object DatasetNamePureConfigInstances extends DatasetNamePureConfigInstances

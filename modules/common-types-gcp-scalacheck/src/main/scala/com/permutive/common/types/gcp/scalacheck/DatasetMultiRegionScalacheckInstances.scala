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

package com.permutive.common.types.gcp.scalacheck

import com.permutive.common.types.gcp.DatasetMultiRegion
import org.scalacheck.Arbitrary
import org.scalacheck.Gen

trait DatasetMultiRegionScalacheckInstances {

  def genDatasetMultiRegion: Gen[DatasetMultiRegion] = Gen.oneOf(DatasetMultiRegion.US, DatasetMultiRegion.EU)

  implicit def DatasetMultiRegionArbitrary: Arbitrary[DatasetMultiRegion] = Arbitrary(genDatasetMultiRegion)

}

object DatasetMultiRegionScalacheckInstances extends DatasetMultiRegionScalacheckInstances

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

import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

package object scalacheck {

  def genDatasetMultiRegion: Gen[DatasetMultiRegion] = Gen.oneOf(DatasetMultiRegion.US, DatasetMultiRegion.EU)

  implicit val DatasetMultiRegionArbitrary: Arbitrary[DatasetMultiRegion] = Arbitrary(genDatasetMultiRegion)

  def genDatasetName: Gen[DatasetName] = arbitrary[String].map(DatasetName(_))

  implicit val DatasetNameArbitrary: Arbitrary[DatasetName] = Arbitrary(genDatasetName)

  def genProjectId: Gen[ProjectId] = for {
    first     <- Gen.alphaChar
    rest      <- Gen.alphaLowerStr.map(_.take(28))
    projectId <- ProjectId.fromString(s"$first$rest").fold(_ => Gen.fail, Gen.const)
  } yield projectId

  implicit val ProjectIdArbitrary: Arbitrary[ProjectId] = Arbitrary(genProjectId)

}

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

import com.permutive.common.types.gcp.ProjectId
import org.scalacheck.Arbitrary
import org.scalacheck.Gen

trait ProjectIdScalacheckInstances {

  def genProjectId: Gen[ProjectId] = for {
    first     <- Gen.alphaChar
    rest      <- Gen.alphaLowerStr.map(_.take(28))
    projectId <- ProjectId.fromString(s"$first$rest").fold(_ => Gen.fail, Gen.const)
  } yield projectId

  implicit def ProjectIdArbitrary: Arbitrary[ProjectId] = Arbitrary(genProjectId)

}

object ProjectIdScalacheckInstances extends ProjectIdScalacheckInstances

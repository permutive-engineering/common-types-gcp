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

package com.permutive.common.types.gcp.scalacheck

import com.permutive.common.types.gcp.ProjectId
import munit.ScalaCheckSuite
import org.scalacheck
import org.scalacheck.Prop._

class ProjectIdScalacheckInstancesSuite extends ScalaCheckSuite {

  override protected def scalaCheckTestParameters: scalacheck.Test.Parameters =
    super.scalaCheckTestParameters.withMinSuccessfulTests(10000)

  property("Arbitrary[ProjectId] generates valid ProjectId") {
    forAll { (projectId: ProjectId) =>
      assert(ProjectId.pattern.pattern.matcher(projectId.value).matches())
    }
  }

}

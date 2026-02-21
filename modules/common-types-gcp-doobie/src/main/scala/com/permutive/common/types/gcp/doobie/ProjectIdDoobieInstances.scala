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

package com.permutive.common.types.gcp.doobie

import com.permutive.common.types.gcp.ProjectId
import doobie.Meta

trait ProjectIdDoobieInstances {

  implicit def ProjectIdMeta: Meta[ProjectId] =
    Meta[String].tiemap(ProjectId.fromString)(_.value)

}

object ProjectIdDoobieInstances extends ProjectIdDoobieInstances {

  implicit override val ProjectIdMeta: Meta[ProjectId] =
    super.ProjectIdMeta

}

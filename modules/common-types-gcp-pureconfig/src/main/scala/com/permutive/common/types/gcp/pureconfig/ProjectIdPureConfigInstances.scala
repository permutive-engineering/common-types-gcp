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

package com.permutive.common.types.gcp.pureconfig

import cats.syntax.all._

import com.permutive.common.types.gcp.ProjectId
import pureconfig.ConfigReader
import pureconfig.ConfigWriter
import pureconfig.error.CannotConvert
import pureconfig.error.FailureReason

trait ProjectIdPureConfigInstances {

  sealed abstract class UnableToRetrieveProjectId(override val description: String) extends FailureReason

  implicit def ProjectIdConfigReader: ConfigReader[ProjectId] = ConfigReader[String].emap {
    case "gcp"  => ProjectId.unsafeFromGCP.value.leftMap(new UnableToRetrieveProjectId(_) {})
    case string => ProjectId.fromString(string).leftMap(CannotConvert(string, "ProjectId", _))
  }

  implicit def ProjectIdConfigWriter: ConfigWriter[ProjectId] = ConfigWriter[String].contramap(_.value)

}

object ProjectIdPureConfigInstances extends ProjectIdPureConfigInstances

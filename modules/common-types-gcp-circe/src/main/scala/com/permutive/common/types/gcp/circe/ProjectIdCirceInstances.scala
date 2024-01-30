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

package com.permutive.common.types.gcp.circe

import com.permutive.common.types.gcp.ProjectId
import io.circe.Decoder
import io.circe.Encoder
import io.circe.KeyDecoder
import io.circe.KeyEncoder

trait ProjectIdCirceInstances {

  implicit def ProjectIdEncoder: Encoder[ProjectId] = Encoder[String].contramap(_.value)

  implicit def ProjectIdDecoder: Decoder[ProjectId] = Decoder[String].emap(ProjectId.fromString)

  implicit def ProjectIdKeyEncoder: KeyEncoder[ProjectId] = _.value

  implicit def ProjectIdKeyDecoder: KeyDecoder[ProjectId] = ProjectId.fromString(_).toOption

}

object ProjectIdCirceInstances extends ProjectIdCirceInstances

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

package com.permutive.common.types.gcp.http4s

import cats.syntax.all._

import com.permutive.common.types.gcp.ProjectId
import org.http4s.ParseFailure
import org.http4s.QueryParamDecoder
import org.http4s.QueryParamEncoder
import org.http4s.Uri.Path.SegmentEncoder

trait ProjectIdHttp4sInstances {

  implicit def ProjectIdSegmentEncoder: SegmentEncoder[ProjectId] = SegmentEncoder[String].contramap(_.value)

  implicit def ProjectIdQueryParamDecoder: QueryParamDecoder[ProjectId] = QueryParamDecoder[String].emap { s =>
    ProjectId.fromString(s).leftMap(ParseFailure(s"Failed to decode value: $s as project-id", _))
  }

  implicit def ProjectIdQueryParamEncoder: QueryParamEncoder[ProjectId] = QueryParamEncoder.fromShow

}

object ProjectIdHttp4sInstances extends ProjectIdHttp4sInstances

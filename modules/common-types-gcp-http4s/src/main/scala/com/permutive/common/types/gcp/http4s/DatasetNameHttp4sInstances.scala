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

import com.permutive.common.types.gcp.DatasetName
import org.http4s.QueryParamDecoder
import org.http4s.QueryParamEncoder
import org.http4s.Uri.Path.SegmentEncoder

trait DatasetNameHttp4sInstances {

  implicit def DatasetNameSegmentEncoder: SegmentEncoder[DatasetName] = SegmentEncoder[String].contramap(_.value)

  implicit def DatasetNameQueryParamDecoder: QueryParamDecoder[DatasetName] =
    QueryParamDecoder[String].map(DatasetName(_))

  implicit def DatasetNameQueryParamEncoder: QueryParamEncoder[DatasetName] = QueryParamEncoder.fromShow

}

object DatasetNameHttp4sInstances extends DatasetNameHttp4sInstances

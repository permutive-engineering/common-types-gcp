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

import cats.syntax.all._

import org.http4s.ParseFailure
import org.http4s.QueryParamDecoder
import org.http4s.QueryParamEncoder
import org.http4s.Uri.Path.SegmentEncoder

package object http4s {

  implicit val DatasetMultiRegionSegmentEncoder: SegmentEncoder[DatasetMultiRegion] =
    SegmentEncoder[String].contramap(_.value)

  implicit val DatasetMultiRegionQueryParamDecoder: QueryParamDecoder[DatasetMultiRegion] =
    QueryParamDecoder[String].emap { s =>
      DatasetMultiRegion.fromString(s).leftMap(ParseFailure(s"Failed to decode value: $s as region", _))
    }

  implicit val DatasetMultiRegionQueryParamEncoder: QueryParamEncoder[DatasetMultiRegion] = QueryParamEncoder.fromShow

  implicit val DatasetNameSegmentEncoder: SegmentEncoder[DatasetName] = SegmentEncoder[String].contramap(_.value)

  implicit val DatasetNameQueryParamDecoder: QueryParamDecoder[DatasetName] =
    QueryParamDecoder[String].map(DatasetName(_))

  implicit val DatasetNameQueryParamEncoder: QueryParamEncoder[DatasetName] = QueryParamEncoder.fromShow

  implicit val ProjectIdSegmentEncoder: SegmentEncoder[ProjectId] = SegmentEncoder[String].contramap(_.value)

  implicit val ProjectIdQueryParamDecoder: QueryParamDecoder[ProjectId] = QueryParamDecoder[String].emap { s =>
    ProjectId.fromString(s).leftMap(ParseFailure(s"Failed to decode value: $s as project-id", _))
  }

  implicit val ProjectIdQueryParamEncoder: QueryParamEncoder[ProjectId] = QueryParamEncoder.fromShow

}

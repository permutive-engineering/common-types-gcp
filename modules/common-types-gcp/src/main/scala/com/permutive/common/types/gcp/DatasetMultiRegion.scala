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

package com.permutive.common.types.gcp

import cats._
import cats.syntax.all._

/** @see https://cloud.google.com/bigquery/docs/locations#multi-regions */
sealed abstract class DatasetMultiRegion(val value: String) {

  @SuppressWarnings(Array("scalafix:Disable.toString"))
  override def toString(): String = value

}

object DatasetMultiRegion {

  case object US extends DatasetMultiRegion("US")
  case object EU extends DatasetMultiRegion("EU")

  def unapply(s: String): Option[DatasetMultiRegion] = DatasetMultiRegion.fromString(s).toOption

  /** Returns either a `DatasetMultiRegion`, if the provided string is a valid region, or a string with the error. */
  def fromString(s: String): Either[String, DatasetMultiRegion] = s match {
    case "US" | "us" => Right(US)
    case "EU" | "eu" => Right(EU)
    case s           => Left(s"$s is not a valid BigQuery multi-region")
  }

  /** Returns a `DatasetMultiRegion` inside an effect, if the provided string is a valid region, otherwise returns an
    * error inside the effect.
    */
  def fromStringF[F[_]: ApplicativeThrow](s: String): F[DatasetMultiRegion] = s match {
    case "US" | "us" => ApplicativeThrow[F].pure(US)
    case "EU" | "eu" => ApplicativeThrow[F].pure(EU)
    case s           => ApplicativeThrow[F].raiseError(new InvalidDatasetMultiRegion(s) {})
  }

  sealed abstract class InvalidDatasetMultiRegion(value: String)
      extends RuntimeException(s"$value is not a valid BigQuery multi-region")

  // Cats instances

  implicit val DatasetMultiRegionShow: Show[DatasetMultiRegion] = Show[String].contramap(_.value)

  implicit val DatasetMultiRegionEqHashOrder
      : Eq[DatasetMultiRegion] with Hash[DatasetMultiRegion] with Order[DatasetMultiRegion] =
    new Eq[DatasetMultiRegion] with Hash[DatasetMultiRegion] with Order[DatasetMultiRegion] {

      override def hash(x: DatasetMultiRegion): Int = Hash[String].hash(x.value)

      override def compare(x: DatasetMultiRegion, y: DatasetMultiRegion): Int = Order[String].compare(x.value, y.value)

    }

}

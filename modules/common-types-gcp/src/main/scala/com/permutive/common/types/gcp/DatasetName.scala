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

import cats._
import cats.syntax.all._

final class DatasetName(val value: String) extends AnyVal {

  @SuppressWarnings(Array("scalafix:Disable.toString"))
  override def toString(): String = value

}

object DatasetName {

  def unapply(s: String): Option[DatasetName] = DatasetName(s).some

  def apply(string: String): DatasetName = new DatasetName(string)

  // Cats instances

  implicit val DatasetNameShow: Show[DatasetName] = Show[String].contramap(_.value)

  implicit val DatasetNameEqHashOrder: Eq[DatasetName] with Hash[DatasetName] with Order[DatasetName] =
    new Eq[DatasetName] with Hash[DatasetName] with Order[DatasetName] {

      override def hash(x: DatasetName): Int = Hash[String].hash(x.value)

      override def compare(x: DatasetName, y: DatasetName): Int = Order[String].compare(x.value, y.value)

    }

}

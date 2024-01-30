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

package com.permutive.common.types.gcp

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration

import cats.ApplicativeThrow
import cats.Eq
import cats.Eval
import cats.Hash
import cats.Order
import cats.Show
import cats.syntax.all._

/** Represents the ID of a GCP's project.
  *
  * The project ID must be a unique string of 6 to 30 lowercase letters, digits, or hyphens. It must start with a
  * letter, and cannot have a trailing hyphen.
  *
  * If running inside a GCP's workload the value can be obtained using `ProjectId.unsafefromGCP`.
  */
final class ProjectId private[gcp] (val value: String) extends AnyVal {

  @SuppressWarnings(Array("scalafix:Disable.toString"))
  override def toString(): String = value

}

object ProjectId extends ProjectIdMacros {

  /** Tries to retrieve the current ProjectId from Google's metadata service.
    *
    * This method will only return a valid project ID if run inside a workload.
    */
  def unsafeFromGCP: Eval[Either[String, ProjectId]] = Eval.defer {
    val uri = sys.props
      .get("google.metadata")
      .getOrElse("http://metadata.google.internal")

    val request = HttpRequest
      .newBuilder()
      .GET()
      .uri(URI.create(s"$uri/computeMetadata/v1/project/project-id"))
      .header("Metadata-Flavor", "Google")
      .timeout(Duration.ofSeconds(3))
      .build()

    val client = HttpClient.newHttpClient()

    Eval.later {
      Either
        .catchNonFatal(client.send(request, BodyHandlers.ofString()))
        .leftMap(t => uri + " " + Option(t.getMessage()).getOrElse(s"$t"))
        .filterOrElse(_.statusCode() === 200, "Unable to retrieve project-id from GCP")
        .map(_.body())
        .flatMap(ProjectId.fromString)
    }
  }

  def unapply(s: String): Option[ProjectId] = ProjectId.fromString(s).toOption

  /** Returns either an `ProjectId`, if the provided string is a valid project-id, or a string with the error. */
  def fromString(s: String): Either[String, ProjectId] =
    if (pattern.pattern.matcher(s).matches()) Right(new ProjectId(s))
    else Left(s"$s is not a valid GCP project-id")

  /** Returns an `ProjectId` inside an effect, if the provided string is a valid project-id, otherwise returns an error
    * inside the effect.
    */
  def fromStringF[F[_]: ApplicativeThrow](s: String): F[ProjectId] =
    if (pattern.pattern.matcher(s).matches()) ApplicativeThrow[F].pure(new ProjectId(s))
    else ApplicativeThrow[F].raiseError(new InvalidProjectId(s) {})

  sealed abstract class InvalidProjectId(value: String) extends RuntimeException(s"$value is not a valid project-id")

  // Cats instances

  implicit val ProjectIdShow: Show[ProjectId] = Show[String].contramap(_.value)

  implicit val ProjectIdEqHashOrder: Eq[ProjectId] with Hash[ProjectId] with Order[ProjectId] =
    new Eq[ProjectId] with Hash[ProjectId] with Order[ProjectId] {

      override def hash(x: ProjectId): Int = Hash[String].hash(x.value)

      override def compare(x: ProjectId, y: ProjectId): Int = Order[String].compare(x.value, y.value)

    }

  lazy val pattern = "^[a-z][a-z0-9-]{5,29}(?<!-)".r

}

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

import java.net.ServerSocket

import scala.concurrent.duration._

import cats.effect.IO
import cats.effect.Resource
import cats.syntax.all._

import com.comcast.ip4s.Port
import munit.CatsEffectSuite
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.defaults.HttpPort
import org.http4s.syntax.all._

class ProjectIdSuite extends CatsEffectSuite {

  val routes = HttpRoutes.of[IO] { case GET -> Root / "computeMetadata" / "v1" / "project" / "project-id" =>
    Ok("test-project")
  }

  def nextPort(port: Int) = Resource
    .fromAutoCloseable(IO(new ServerSocket(port)))
    .attempt
    .use(_.bimap(_ => port + 1, _.getLocalPort).pure[IO])

  val fixture = ResourceFunFixture {
    HttpPort
      .tailRecM(nextPort)
      .flatMap(Port.fromInt(_).get.pure[IO])
      .toResource
      .map(EmberServerBuilder.default[IO].withPort(_).withShutdownTimeout(1.milli).withHttpApp(routes.orNotFound))
      .flatMap(_.build)
      .evalTap(server => IO(System.setProperty("google.metadata", s"http://localhost:${server.address.getPort()}")))
      .onFinalize(IO(System.clearProperty("google.metadata")).void)
  }

  fixture.test("ProjectId can be loaded from GCP") { _ =>
    val result = ProjectId.unsafeFromGCP.value

    assertEquals(result, Right(ProjectId("test-project")))
  }

  test("ProjectId.random works on tests") {
    val projectId1 = ProjectId.random()
    val projectId2 = ProjectId.random()

    assert(ProjectId.pattern.pattern.matcher(projectId1.value).matches())
    assert(ProjectId.pattern.pattern.matcher(projectId2.value).matches())
    assertNotEquals(projectId1, projectId2)
  }

  test("ProjectId.apply with literal string works") {
    val projectId = ProjectId("permutive-test")

    assert(ProjectId.pattern.pattern.matcher(projectId.value).matches())

    val expected = ProjectId("permutive-test")
    assertEquals(projectId, expected)
  }

  test("ProjectId.apply with literal fails if not a literal") {
    val scala2 = """error: compile-time refinement only works with literals, use ProjectId.fromString instead
                   |val a = ""; ProjectId(a)
                   |                     ^""".stripMargin

    val scala3 = """error: compile-time refinement only works with literals, use ProjectId.fromString instead
                   |      .getOrElse(assertNoDiff(compileErrors("val a = \"\"; ProjectId(a)"), scala3))
                   |                                          ^""".stripMargin

    Either
      .catchNonFatal(assertNoDiff(compileErrors("val a = \"\"; ProjectId(a)"), scala2))
      .getOrElse(assertNoDiff(compileErrors("val a = \"\"; ProjectId(a)"), scala3))
  }

  test("ProjectId.apply with literal fails if not a valid project-id") {
    val scala2 = """error: 42 is not a valid GCP project-id
                   |ProjectId("42")
                   |         ^""".stripMargin

    val scala3 = """error: 42 is not a valid GCP project-id
                   |      .getOrElse(assertNoDiff(compileErrors("ProjectId(\"42\")"), scala3))
                   |                                          ^""".stripMargin

    Either
      .catchNonFatal(assertNoDiff(compileErrors("""ProjectId("42")"""), scala2))
      .getOrElse(assertNoDiff(compileErrors("ProjectId(\"42\")"), scala3))
  }

}

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

import scala.reflect.macros.blackbox
import scala.util.Random

private[gcp] trait ProjectIdMacros {

  /** Creates an `ProjectId` from a literal UUID. */
  def apply(s: String): ProjectId = macro ProjectIdMacrosInternal.literal

  /** Creates a random `ProjectId`. This method will raise a warning if used outside the `test` or `it` configurations.
    *
    * If you know what you're doing, you can use the `@nowarn` annotation to suppress the warning.
    */
  def random(): ProjectId = macro ProjectIdMacrosInternal.random

}

private[gcp] class ProjectIdMacrosInternal(val c: blackbox.Context) extends MacroUtils {

  import c.universe._

  def literal(s: c.Expr[String]): c.Expr[ProjectId] = {
    val value = asLiteral(s, or = "ProjectId.fromString")

    if (ProjectId.pattern.pattern.matcher(value).matches())
      c.Expr(q"_root_.com.permutive.common.types.gcp.ProjectId.fromString($s).toOption.get")
    else
      abort(s"$value is not a valid GCP project-id")
  }

  def random(): c.Expr[ProjectId] = {
    val path = c.enclosingPosition.source.path
    if (!path.contains("/test/") && !path.contains("/it/"))
      warn("You should only use this in tests. You can use @nowarn to suppress this warning.")

    c.universe.reify {
      val value = Random.alphanumeric.dropWhile(char => !char.isLetter).take(15).mkString.toLowerCase()

      ProjectId.fromString(value).toOption.get
    }
  }

}

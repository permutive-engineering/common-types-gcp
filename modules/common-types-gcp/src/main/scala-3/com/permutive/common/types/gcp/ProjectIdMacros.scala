package com.permutive.common.types.gcp

import scala.quoted.*
import scala.util.Random

private[gcp] trait ProjectIdMacros {

  /** Creates an `ProjectId` from a literal UUID. */
  inline def apply(inline s: String): ProjectId = ${ ProjectIdMacros.literal('{ s }) }

  /** Creates a random `ProjectId`. This method will raise a warning if used outside the `test` or `it` configurations.
    *
    * If you know what you're doing, you can use the `@nowarn` annotation to suppress the warning.
    */
  inline def random(): ProjectId = ${ ProjectIdMacros.random() }

}

private[gcp] object ProjectIdMacros {

  def literal(s: Expr[String])(using Quotes): Expr[ProjectId] =
    import quotes.reflect._

    val string = s.value.getOrElse {
      val position = Position.ofMacroExpansion
      report.errorAndAbort(
        "compile-time refinement only works with literals, use ProjectId.fromString instead",
        position
      )
    }

    ProjectId
      .fromString(string)
      .fold(report.errorAndAbort(_), _ => '{ ProjectId.fromString(${ Expr(string) }).toOption.get })

  def random()(using q: Quotes): Expr[ProjectId] =
    import quotes.reflect._

    if (!SourceFile.current.path.contains("/test/") && !SourceFile.current.path.contains("/it/"))
      report.warning("You should only use this in tests. You can use @nowarn to suppress this warning.")

    '{
      val value = Random.alphanumeric.dropWhile(char => !char.isLetter).take(15).mkString.toLowerCase()

      ProjectId.fromString(value).toOption.get
    }

}

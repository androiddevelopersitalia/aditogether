package aditogether.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtNamedFunction

/**
 * Forbid local functions.
 *
 * Compliant code:
 * ```kotlin
 * fun foo() {
 *     bar()
 * }
 *
 * private fun bar() {
 *     println("bar")
 * }
 * ```
 *
 * Non-compliant code:
 * ```kotlin
 * fun foo() {
 *     fun bar() {
 *         println("bar")
 *     }
 *     bar()
 * }
 */
class ForbiddenLocalFunction : Rule() {

    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.Minor,
        description = DESCRIPTION,
        debt = Debt.TEN_MINS
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        if (function.isLocal) {
            report(CodeSmell(issue, Entity.from(function), MESSAGE))
        }
    }

    private companion object {

        const val DESCRIPTION = "Local functions are forbidden."
        const val MESSAGE = "Local function makes the code harder to read and understand. " +
            "Consider extracting it to a regular private function."
    }
}

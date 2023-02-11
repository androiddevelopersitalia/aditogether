package aditogether.detekt.rules

import io.gitlab.arturbosch.detekt.test.lint
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize

class ForbiddenLocalFunctionTest : BehaviorSpec({

    val rule = ForbiddenLocalFunction()

    Given("a file with local functions") {
        val code = """
            fun foo() {
                fun bar() {
                    println("bar")
                }
                bar()
            }
        """.trimIndent()

        When("linting the file") {
            val findings = rule.lint(code)

            Then("it should report a local function") {
                findings shouldHaveSize 1
            }
        }
    }

    Given("a file without local functions") {
        val code = """
            fun foo() {
                bar()
            }
            
            private fun bar() {
                println("bar")
            }
        """.trimIndent()

        When("linting the file") {
            val findings = rule.lint(code)

            Then("it should not report any local functions") {
                findings shouldHaveSize 0
            }
        }
    }

    Given("a file with lambda function inside a function") {
        val code = """
            fun foo() {
                val bar = { println("bar") }
                bar()
            }
        """.trimIndent()

        When("linting the file") {
            val findings = rule.lint(code)

            Then("it should not report any local functions") {
                findings shouldHaveSize 0
            }
        }
    }
})

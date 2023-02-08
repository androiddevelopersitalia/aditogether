package aditogether.playground.test

import io.kotest.core.spec.style.BehaviorSpec

class KotestPlaygroundTest : BehaviorSpec({
    Given("a test") {
        When("I run it") {
            Then("it should pass") {
                assert(true)
            }
        }
    }
})

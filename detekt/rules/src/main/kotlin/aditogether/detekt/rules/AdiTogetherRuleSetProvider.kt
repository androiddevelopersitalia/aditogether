package aditogether.detekt.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class AdiTogetherRuleSetProvider : RuleSetProvider {

    override val ruleSetId: String = "AdiTogether"

    override fun instance(config: Config) = RuleSet(
        id = ruleSetId,
        rules = listOf(
            ForbiddenLocalFunction()
        )
    )
}

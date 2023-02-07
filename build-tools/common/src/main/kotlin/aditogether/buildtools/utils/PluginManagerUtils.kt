package aditogether.buildtools.utils

import org.gradle.api.Plugin
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.plugins.PluginManager

/**
 * Applies the plugin of the given class [P].
 */
inline fun <reified P : Plugin<*>> PluginManager.apply() {
    apply(P::class.java)
}

/**
 * Invokes [PluginManager.withPlugin] for each plugin in the inputs.
 * This is useful to apply the same action for a list of plugins.
 */
fun PluginManager.withPlugins(
    first: String,
    vararg others: String,
    action: (AppliedPlugin) -> Unit
) {
    withPlugin(first, action)
    others.forEach { plugin -> withPlugin(plugin, action) }
}

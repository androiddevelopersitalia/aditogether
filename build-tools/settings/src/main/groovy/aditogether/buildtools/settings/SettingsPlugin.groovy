package aditogether.buildtools.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

/**
 * Applies and configures the same plugins to our settings.gradle files.
 */
@SuppressWarnings('unused')
class SettingsPlugin implements Plugin<Settings> {
    @Override
    void apply(Settings target) {
        target.apply plugin: "org.gradle.toolchains.foojay-resolver-convention"
    }
}

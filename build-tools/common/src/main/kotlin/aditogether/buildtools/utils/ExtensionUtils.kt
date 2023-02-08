package aditogether.buildtools.utils

import org.gradle.api.plugins.ExtensionContainer

/**
 * Invokes Groovy's [ExtensionContainer.getByType] reifying the type [T].
 */
inline fun <reified T : Any> ExtensionContainer.getByType(): T = getByType(T::class.java)

/**
 * Invokes Groovy's [ExtensionContainer.configure] reifying the type [T].
 */
inline fun <reified T : Any> ExtensionContainer.configure(noinline action: (T) -> Unit) {
    configure(T::class.java, action)
}

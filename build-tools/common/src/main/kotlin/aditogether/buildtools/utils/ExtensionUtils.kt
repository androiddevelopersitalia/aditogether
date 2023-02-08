package aditogether.buildtools.utils

import org.gradle.api.plugins.ExtensionContainer

/**
 * Invokes Groovy's [ExtensionContainer.getByType] reifying the type [T].
 */
inline fun <reified T : Any> ExtensionContainer.getByType(): T = getByType(T::class.java)

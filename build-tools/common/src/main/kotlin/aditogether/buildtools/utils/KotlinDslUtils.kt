package aditogether.buildtools.utils

import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.PluginAware
import org.gradle.api.reflect.TypeOf

/**
 * Applies the plugin of the given type [P]. Does nothing if the plugin has already been applied.
 *
 * The given class should implement the [Plugin] interface, and be parameterized for a
 * compatible type of `this`.
 *
 * @param P the plugin type.
 * @see [PluginAware.apply]
 */
inline fun <reified P : Plugin<*>> Project.apply() {
    plugins.apply(P::class.java)
}

/**
 * Returns a collection containing the objects in this collection of the given type. Equivalent to calling
 * {@code withType(type).all(configureAction)}
 *
 * @param S The type of objects to find.
 * @param configuration The action to execute for each object in the resulting collection.
 * @return The matching objects. Returns an empty collection if there are no such objects
 * in this collection.
 * @see [DomainObjectCollection.withType]
 */
inline fun <reified S : Any> DomainObjectCollection<in S>.withType(
    noinline configuration: S.() -> Unit
): DomainObjectCollection<S> = withType(S::class.java, configuration)

/**
 * Looks for the extension of a given type.
 *
 * @param T the extension type
 * @return the extension
 * @throws UnknownDomainObjectException when no matching extension can be found
 *
 * @see [ExtensionContainer.getByType]
 * @since 5.0
 */
inline fun <reified T : Any> ExtensionContainer.getByType(): T =
    getByType(typeOf<T>())

/**
 * Creates an instance of [TypeOf] for the given parameterized type.
 *
 * @param T the type
 * @return the [TypeOf] that captures the generic type of the given parameterized type
 */
inline fun <reified T> typeOf(): TypeOf<T> =
    object : TypeOf<T>() {}

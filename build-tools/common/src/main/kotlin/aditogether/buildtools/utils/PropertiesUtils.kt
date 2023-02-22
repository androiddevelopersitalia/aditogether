package aditogether.buildtools.utils

import groovy.lang.MissingPropertyException
import org.gradle.api.Project

/**
 * Returns the Gradle property with the name [name] in a string format.
 * @throws MissingPropertyException if the property is not present.
 */
@Throws(MissingPropertyException::class)
fun Project.stringProperty(name: String): String = property(name).toString()

/**
 * Returns the Gradle property with the name [name] in a integer format.
 * @throws MissingPropertyException if the property is not present or does not conform to an integer.
 */
@Throws(MissingPropertyException::class)
fun Project.intProperty(name: String): Int = requireNotNull(stringProperty(name).toIntOrNull()) {
    "The Gradle property named $name exists but is not an integer."
}

/**
 * Returns the Gradle property with the name [name] in a integer format.
 * @throws MissingPropertyException if the property is not present or does not conform to a boolean.
 */
@Throws(MissingPropertyException::class)
fun Project.boolProperty(name: String): Boolean {
    return requireNotNull(stringProperty(name).toBooleanStrictOrNull()) {
        "The Gradle property named $name exists but is not a boolean."
    }
}

package io.github.portlek.smol.func

import io.github.portlek.smol.SMOL_API_CONFIGURATION_NAME
import io.github.portlek.smol.SMOL_CONFIGURATION_NAME
import org.gradle.api.Action
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider
import org.gradle.internal.Cast.uncheckedCast
import org.gradle.kotlin.dsl.add

fun DependencyHandler.smol(
    dependencyNotation: Provider<*>,
    dependencyOptions: Action<in ExternalModuleDependency>
) {
  addProvider(SMOL_CONFIGURATION_NAME, dependencyNotation, dependencyOptions)
}

fun DependencyHandler.smolApi(
    dependencyNotation: Provider<*>,
    dependencyOptions: Action<ExternalModuleDependency>
) {
  addProvider(SMOL_API_CONFIGURATION_NAME, dependencyNotation, dependencyOptions)
}

fun DependencyHandler.smol(dependencyNotation: Any) =
    add(SMOL_CONFIGURATION_NAME, dependencyNotation)

fun DependencyHandler.smolApi(dependencyNotation: Any) =
    add(SMOL_API_CONFIGURATION_NAME, dependencyNotation)

fun DependencyHandler.smol(
    dependencyNotation: String,
    dependencyConfiguration: ExternalModuleDependency.() -> Unit
) = add(SMOL_CONFIGURATION_NAME, dependencyNotation, dependencyConfiguration)

fun DependencyHandler.smolApi(
    dependencyNotation: String,
    dependencyConfiguration: ExternalModuleDependency.() -> Unit
) = add(SMOL_API_CONFIGURATION_NAME, dependencyNotation, dependencyConfiguration)

private fun DependencyHandler.withOptions(
    configuration: String,
    dependencyNotation: String,
    dependencyConfiguration: Action<ExternalModuleDependency>?
): ExternalModuleDependency? = run {
  uncheckedCast<ExternalModuleDependency>(create(dependencyNotation)).also { dependency ->
    if (dependency == null) return@run null
    dependencyConfiguration?.execute(dependency)
    add(configuration, dependency)
  }
}

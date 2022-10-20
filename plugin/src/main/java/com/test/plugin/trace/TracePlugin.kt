package com.test.plugin.trace

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.test.plugin.trace.internal.Matcher
import com.test.plugin.trace.internal.TraceDefaultConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

class TracePlugin : Plugin<Project> {

    companion object {
        private const val EXTENSION_NAME = "trace"
    }

    override fun apply(project: Project) {
        project.logger.log(LogLevel.INFO, "apply tracer")

        project.extensions.create(EXTENSION_NAME, TraceExtension::class.java)

        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            val ext = project.extensions.findByName(EXTENSION_NAME) as TraceExtension
            variant.instrumentation.transformClassesWith(
                TraceTransform::class.java,
                InstrumentationScope.ALL
            ) {
                val ignoreClasses = ext.ignoreClass.get() + TraceDefaultConfig.defaultIgnoreClasses
                it.matcher.set(Matcher(ignoreClasses.toSet()))
            }
            variant.instrumentation.setAsmFramesComputationMode(
                FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS
            )
        }
    }
}
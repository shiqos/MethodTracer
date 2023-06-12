package com.test.plugin.trace

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.android.utils.appendCapitalized
import com.test.plugin.trace.internal.filter.TraceClassFilter
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
        val variants = mutableListOf<Pair<Variant, TraceContext>>()
        androidComponents.onVariants { variant ->
            val context = TraceContext(project, variant)
            variant.instrumentation.transformClassesWith(
                TraceTransform::class.java, InstrumentationScope.ALL
            ) {
                val ext = project.extensions.findByName(EXTENSION_NAME) as TraceExtension

                it.context.set(context)
                val ignoreClasses = ext.ignoreClass.get()
                it.classFilter.set(TraceClassFilter(ignoreClasses))
            }
            variant.instrumentation.setAsmFramesComputationMode(
                FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS
            )

            variants.add(Pair(variant, context))
        }

        project.afterEvaluate {
            variants.forEach { item ->
                val taskName = "assemble".appendCapitalized(item.first.name)
                println("trace -> taskName=$taskName")
                project.tasks.findByName(taskName)?.doFirst {
                    println("trace -> onFinish")
                    item.second.onFinish()
                } ?: run {
                    println("trace -> can not find $taskName")
                }
            }
        }

    }
}
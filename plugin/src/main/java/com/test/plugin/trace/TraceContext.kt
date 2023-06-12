package com.test.plugin.trace

import com.android.build.api.variant.Variant
import org.gradle.api.Project
import java.io.File
import java.io.Serializable

class TraceContext(project: Project, variant: Variant) : Serializable {

    private val methodMapFile: File by lazy {
        File(project.buildDir, "trace/${variant.name}/methodMapping.txt")
    }

    private val variantName = variant.name

    fun methodRegistry(): MethodRegistry {
        return MethodRegistry.getMethodRegistry(variantName)
    }

    fun onFinish() {
        val methodMap = methodRegistry().getMap()
        println("trace -> methodMap: ${methodMap.size}")

        methodMapFile.parentFile.mkdirs()
        methodMapFile.writer().use { writer ->
            methodMap.forEach {
                writer.write("${it.second}->${it.first}\n")
            }
        }
    }

}
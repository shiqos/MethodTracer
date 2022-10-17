package com.test.plugin.trace

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.test.plugin.trace.internal.Matcher
import com.test.plugin.trace.internal.TraceDefaultConfig
import com.test.plugin.trace.internal.visitor.TraceClassVisitor
import org.objectweb.asm.ClassVisitor

abstract class TraceTransform : AsmClassVisitorFactory<TraceParams> {
    companion object {
        private const val TAG = "TraceTransform"
    }

    private val classMatcher: Matcher by lazy {
        val set = parameters.get().ignoreClass + TraceDefaultConfig.defaultIgnoreClasses
        Matcher(set)
    }

    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        return TraceClassVisitor(nextClassVisitor, classContext.currentClassData.className)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return !classMatcher.match(classData.className)
    }
}
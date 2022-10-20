package com.test.plugin.trace

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.test.plugin.trace.internal.visitor.TraceClassVisitor
import org.objectweb.asm.ClassVisitor

abstract class TraceTransform : AsmClassVisitorFactory<TraceParams> {
    companion object {
        private const val TAG = "TraceTransform"
    }

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return TraceClassVisitor(nextClassVisitor, classContext.currentClassData.className)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        val matcher = parameters.get().matcher.get()
        return !matcher.match(classData.className)
    }
}
package com.test.plugin.trace

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.test.plugin.trace.internal.bytecode.TraceClassVisitor
import org.objectweb.asm.ClassVisitor

abstract class TraceTransform : AsmClassVisitorFactory<TraceParams> {
    companion object {
        private const val TAG = "TraceTransform"
    }

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return TraceClassVisitor(
            classContext.currentClassData.className,
            nextClassVisitor
        )
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        val filter = parameters.get().classFilter.get()
        return !filter.filter(classData.className)
    }
}
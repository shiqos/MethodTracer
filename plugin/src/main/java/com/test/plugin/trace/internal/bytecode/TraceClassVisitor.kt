package com.test.plugin.trace.internal.bytecode

import com.test.plugin.trace.TraceContext
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

open class TraceClassVisitor(
    private val className: String,
    private val context: TraceContext,
    nextVisitor: ClassVisitor
) :
    ClassVisitor(Opcodes.ASM9, nextVisitor) {

    companion object {
        private const val TAG = "TraceClassVisitor"
    }

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        val methodDesc = MethodDesc(className, name, descriptor)
        val methodId = context.methodRegistry().generateId(methodDesc)
        return TraceMethodVisitor(methodId, Opcodes.ASM9, visitor, access, name, descriptor)
    }
}
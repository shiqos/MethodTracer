package com.test.plugin.trace.internal.bytecode

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

open class TraceClassVisitor(
    private val className: String,
    nextVisitor: ClassVisitor
) :
    ClassVisitor(Opcodes.ASM9, nextVisitor) {

    companion object {
        private const val TAG = "TraceClassVisitor"
    }

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        println("visitMethod: $className#$name, descriptor=$descriptor")

        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return TraceMethodVisitor(className, TraceMethodTagGenerator(), Opcodes.ASM9, visitor, access, name, descriptor)
    }
}
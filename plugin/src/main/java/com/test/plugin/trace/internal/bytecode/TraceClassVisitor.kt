package com.test.plugin.trace.internal.bytecode

import com.test.plugin.trace.internal.TraceTagComputer
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.INVOKESTATIC

open class TraceClassVisitor(nextVisitor: ClassVisitor, private val className: String) :
    ClassVisitor(Opcodes.ASM5, nextVisitor) {

    companion object {
        private const val TAG = "TraceClassVisitor"

        private const val TRACE_CLASS = "com/test/trace/Tracer"
        private const val TRACE_BEGIN_METHOD = "b"
        private const val TRACE_END_METHOD = "e"
        private const val TRACE_METHOD_DESC = "(Ljava/lang/String;)V"

        private const val APPLICATION_LIKE_CLASS = "com/bomber/lancer/TraceApplicationLike"
        private const val APPLICATION_LIKE_ATTACHBASECONTEXT = "(Landroid/content/Context;)V"

        private const val SUPER_APPLICATION_CLASS = "android/app/Application"
        private const val SUPER_MULTI_APPLICATION_CLASS = "androidx/multidex/MultiDexApplication"
        private const val APPLICATION_ATTACHCONTEXT_NAME = "attachBaseContext"
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
        println("visitMethod: $className#$name, signature=$signature, signature=$descriptor")

        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return object : TraceMethodVisitor(Opcodes.ASM5, visitor, access, name, descriptor) {
            override fun beforeMethod() {
                val desc = MethodDesc(className, name, descriptor)
                injectBeforeMethod(mv, desc)
            }

            override fun afterMethod() {
                val desc = MethodDesc(className, name, descriptor)
                injectAfterMethod(mv, desc)
            }
        }
    }

    private fun injectBeforeMethod(mv: MethodVisitor, desc: MethodDesc) {
        mv.visitLdcInsn(TraceTagComputer.getTag(desc))
        mv.visitMethodInsn(
            INVOKESTATIC,
            TRACE_CLASS,
            TRACE_BEGIN_METHOD,
            TRACE_METHOD_DESC,
            false
        )
    }

    private fun injectAfterMethod(mv: MethodVisitor, desc: MethodDesc) {
        mv.visitLdcInsn(TraceTagComputer.getTag(desc))
        mv.visitMethodInsn(
            INVOKESTATIC,
            TRACE_CLASS,
            TRACE_END_METHOD,
            TRACE_METHOD_DESC,
            false
        )
    }
}
package com.test.plugin.trace.internal.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.INVOKESTATIC
import org.objectweb.asm.commons.AdviceAdapter

open class TraceClassVisitor(nextVisitor: ClassVisitor, private val className: String)
    : ClassVisitor(Opcodes.ASM5, nextVisitor) {

    companion object {
        private const val TAG = "TraceClassVisitor"

        private const val TRACE_CLASS = "com/bomber/lancer/core/SysTracer"
        private const val TRACE_METHOD_DESC = "(Ljava/lang/String;Ljava/lang/String;)V"

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
        println("visitMethod: $className#$name")

        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return object : TraceMethodVisitor(Opcodes.ASM5, visitor, access, name, descriptor) {
            override fun beforeMethod() {
                injectBeforeMethod(mv, className, name)
            }

            override fun afterMethod() {
                injectAfterMethod(mv, className, name)
            }
        }
    }

    private fun injectBeforeMethod(mv: MethodVisitor, className: String, methodName: String) {
        mv.visitLdcInsn(className)
        mv.visitLdcInsn(methodName)
        mv.visitMethodInsn(
            AdviceAdapter.INVOKESTATIC,
            TRACE_CLASS,
            "i",
            TRACE_METHOD_DESC,
            false
        )
    }

    private fun injectAfterMethod(mv: MethodVisitor, className: String, methodName: String) {
        mv.visitLdcInsn(className)
        mv.visitLdcInsn(methodName)
        mv.visitMethodInsn(
            INVOKESTATIC,
            TRACE_CLASS, 
            "o",
            TRACE_METHOD_DESC,
            false
        )
    }
}
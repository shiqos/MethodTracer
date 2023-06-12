package com.test.plugin.trace.internal.bytecode

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

open class TraceMethodVisitor(
    private val methodId: Int,
    api: Int,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {

    companion object {
        private const val TAG = "TraceMethodVisitor"

        private const val TRACE_CLASS = "com/test/trace/Tracer"
        private const val TRACE_BEGIN_METHOD = "i"
        private const val TRACE_END_METHOD = "o"
        private const val TRACE_METHOD_DESC = "(I)V"
    }

    override fun onMethodEnter() {
        mv.visitLdcInsn(methodId)
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            TRACE_CLASS,
            TRACE_BEGIN_METHOD,
            TRACE_METHOD_DESC,
            false
        )
    }

    override fun onMethodExit(opcode: Int) {
        mv.visitLdcInsn(methodId)
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            TRACE_CLASS,
            TRACE_END_METHOD,
            TRACE_METHOD_DESC,
            false
        )
    }

}
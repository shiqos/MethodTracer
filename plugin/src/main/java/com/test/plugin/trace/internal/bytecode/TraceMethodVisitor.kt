package com.test.plugin.trace.internal.bytecode

import com.test.plugin.trace.util.Generator
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

open class TraceMethodVisitor(
    private val className: String,
    private val tagGenerator: Generator<MethodDesc, String>,
    api: Int,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {

    companion object {
        private const val TAG = "TraceMethodVisitor"

        private const val TRACE_CLASS = "com/test/trace/Tracer"
        private const val TRACE_BEGIN_METHOD = "b"
        private const val TRACE_END_METHOD = "e"
        private const val TRACE_METHOD_DESC = "(Ljava/lang/String;)V"
    }

    override fun onMethodEnter() {
        val desc = MethodDesc(className, name, methodDesc)
        mv.visitLdcInsn(tagGenerator.generate(desc))
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            TRACE_CLASS,
            TRACE_BEGIN_METHOD,
            TRACE_METHOD_DESC,
            false
        )
    }

    override fun onMethodExit(opcode: Int) {
        val desc = MethodDesc(className, name, methodDesc)
        mv.visitLdcInsn(tagGenerator.generate(desc))
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            TRACE_CLASS,
            TRACE_END_METHOD,
            TRACE_METHOD_DESC,
            false
        )
    }

}
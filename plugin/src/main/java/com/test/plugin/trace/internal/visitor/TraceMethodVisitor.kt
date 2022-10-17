package com.test.plugin.trace.internal.visitor

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

open class TraceMethodVisitor(
    api: Int,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?
) :  AdviceAdapter(api, methodVisitor, access, name, descriptor) {
    companion object {
        private const val TAG = "TraceMethodVisitor"
    }

    protected open fun beforeMethod() {}

    protected open fun afterMethod() {}

    private var handler: Label? = null
    private var state = State.NONE

    override fun onMethodEnter() {
        beforeMethod()
        super.onMethodEnter()
    }

    override fun onMethodExit(opcode: Int) {
        afterMethod()
        super.onMethodExit(opcode)
    }

    override fun visitTryCatchBlock(start: Label?, end: Label?, handler: Label?, type: String?) {
        super.visitTryCatchBlock(start, end, handler, type)
        this.handler = handler
    }

    override fun visitLabel(label: Label?) {
        super.visitLabel(label)

        state = if (label != null && handler == label) {
            State.LABEL
        } else {
            State.NONE
        }
    }

    override fun visitFrame(type: Int, numLocal: Int, local: Array<out Any>?, numStack: Int, stack: Array<out Any>?) {
        super.visitFrame(type, numLocal, local, numStack, stack)

        if (state == State.LABEL) {
            state = State.FRAME
        }
    }

    override fun visitVarInsn(opcode: Int, `var`: Int) {
        super.visitVarInsn(opcode, `var`)

        if (state == State.FRAME && opcode == ASTORE) {
            afterMethod()
        }
    }

    enum class State {
        NONE,
        LABEL,
        FRAME
    }
}
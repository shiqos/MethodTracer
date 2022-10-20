package com.test.plugin.trace.internal

import com.test.plugin.trace.internal.bytecode.MethodDesc

object TraceTagComputer {

    private const val MAX_TAG_NAME_LEN = 127
    private const val LONG_PREFIX = "..."

    fun getTag(desc: MethodDesc): String {
        val tag = getDefaultTag(desc)
        return if (tag.length <= MAX_TAG_NAME_LEN) {
             tag
        } else {
            LONG_PREFIX + tag.substring(tag.length - MAX_TAG_NAME_LEN + LONG_PREFIX.length)
        }
    }

    private fun getDefaultTag(desc: MethodDesc): String {
        return if (desc.descriptor.isNullOrEmpty()) {
            "${desc.className}#${desc.methodName}"
        } else {
            val code = desc.descriptor.hashCode().toString(16)
            "${desc.className}#${desc.methodName}-$code"
        }
    }

}
package com.test.plugin.trace.internal.bytecode

import com.test.plugin.trace.util.Generator

class TraceMethodTagGenerator : Generator<MethodDesc, String>, java.io.Serializable {

    companion object {
        private const val MAX_TAG_NAME_LEN = 127
        private const val LONG_PREFIX = "*."
    }

    override fun generate(input: MethodDesc): String {
        val tag = getDefaultTag(input)
        return if (tag.length <= MAX_TAG_NAME_LEN) {
            tag
        } else {
            LONG_PREFIX + tag.substring(tag.length - MAX_TAG_NAME_LEN + LONG_PREFIX.length)
        }
    }

    private fun getDefaultTag(desc: MethodDesc): String {
        return "${desc.className}#${desc.methodName}"
    }

    private fun getDefaultTagWithCode(desc: MethodDesc): String {
        return if (desc.descriptor.isNullOrEmpty()) {
            "${desc.className}#${desc.methodName}"
        } else {
            val code = desc.descriptor.hashCode().toString(16)
            "${desc.className}#${desc.methodName}-$code"
        }
    }

}
package com.test.plugin.trace.internal.bytecode

data class MethodDesc(
    val className: String,
    val methodName: String,
    val descriptor: String?
) {
    val key by lazy {
        "$className:$methodName$descriptor"
    }
}
package com.test.plugin.trace.internal

object TraceDefaultConfig {

    val defaultIgnoreClasses = setOf(
        "com.test.plugin.trace.*",

        "androidx.*",
        "kotlin.*",
        "kotlinx.*",
    )

}
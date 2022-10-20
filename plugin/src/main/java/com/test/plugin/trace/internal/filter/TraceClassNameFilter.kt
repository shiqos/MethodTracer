package com.test.plugin.trace.internal.filter

import com.test.plugin.trace.util.Filter

class TraceClassNameFilter: Filter<String>, java.io.Serializable {

    companion object {
        private val defaultIgnoreClassNames = setOf(
            "R",
            "Manifest",
            "BuildConfig"
        )

        private val defaultIgnoreClassPrefixes = setOf(
            "R$"
        )
    }

    override fun filter(input: String): Boolean {
        val name = input.split(".").last()
        return when {
            name in defaultIgnoreClassNames -> true
            defaultIgnoreClassPrefixes.any { name.startsWith(it) } -> true
            else -> false
        }
    }
}
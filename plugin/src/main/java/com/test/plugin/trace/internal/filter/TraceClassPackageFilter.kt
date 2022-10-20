package com.test.plugin.trace.internal.filter

import com.test.plugin.trace.util.Filter
import java.util.regex.Pattern

class TraceClassPackageFilter(ignoreClasses: List<String>) : Filter<String>, java.io.Serializable {

    companion object {
        private val defaultPluginIgnoreClasses = setOf(
            "com.test.plugin.trace.*",
            "com.test.trace.*"
        )
    }

    private val patterns = (ignoreClasses + defaultPluginIgnoreClasses).map {
        Pattern.compile(it)
    }

    override fun filter(input: String): Boolean {
        return patterns.any {
            it.matcher(input).matches()
        }
    }
}
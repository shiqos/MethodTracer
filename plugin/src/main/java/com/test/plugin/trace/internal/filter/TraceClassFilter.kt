package com.test.plugin.trace.internal.filter

import com.test.plugin.trace.util.Filter

class TraceClassFilter(ignoreClasses: List<String>) : java.io.Serializable {

    private val filters: List<Filter<String>> = listOf(
        TraceClassNameFilter(),
        TraceClassPackageFilter(ignoreClasses)
    )

    fun filter(className: String): Boolean {
        return filters.any {
            it.filter(className)
        }
    }

}
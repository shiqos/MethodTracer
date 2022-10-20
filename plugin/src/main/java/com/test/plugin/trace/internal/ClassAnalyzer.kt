package com.test.plugin.trace.internal

/**
 * TODO: Add Description
 *
 * @author saulshi
 * @date 2022/10/17
 */
class ClassAnalyzer(private val ignoreClass: List<String>) {
    companion object {
        private const val TAG = "ClassAnalyzer"
    }

    fun shouldIgnore(className: String): Boolean {
        return true
    }
}
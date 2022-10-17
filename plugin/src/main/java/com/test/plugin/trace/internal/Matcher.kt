package com.test.plugin.trace.internal

import java.util.regex.Pattern

class Matcher(rawPatterns: Set<String>) {
    companion object {
        private const val TAG = "Matcher"
    }

    private val patterns = rawPatterns.map {
        Pattern.compile(it)
    }

    fun match(text: String): Boolean {
        return patterns.any {
            it.matcher(text).matches()
        }
    }

}
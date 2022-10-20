package com.test.plugin.trace.util

import java.io.Serializable
import java.util.regex.Pattern

class StringMatcher(regexes: Set<String>) : Serializable {
    companion object {
        private const val TAG = "Matcher"
    }

    private val patterns = regexes.map {
        Pattern.compile(it)
    }

    fun match(text: String): Boolean {
        return patterns.any {
            it.matcher(text).matches()
        }
    }

}
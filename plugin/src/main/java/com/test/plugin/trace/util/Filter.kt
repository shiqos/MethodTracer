package com.test.plugin.trace.util

interface Filter<T> {

    fun filter(input: T): Boolean

}
package com.test.plugin.trace.util

interface Generator<I, O> {

    fun generate(input: I): O

}
package com.test.plugin.trace.internal

import org.junit.Test

class TraceTagComputerTest {

    @Test
    fun test_compute() {
        val className = "1"
        val methodName = "23"

        val result = TraceTagComputer.getTag(className, methodName)
        val expect = "1.23"

        assert(result == expect)
    }

    @Test
    fun test_compute_class_long() {
        val className = "123"
        val methodName = "23"

        val result = TraceTagComputer.getTag(className, methodName)
        val expect = "...23"

        assert(result == expect)
    }

    @Test
    fun test_compute_method() {
        val className = "1"
        val methodName = "123"

        val result = TraceTagComputer.getTag(className, methodName)
        val expect = "1.123"

        assert(result == expect)
    }

    @Test
    fun test_compute_method_long() {
        val className = "12"
        val methodName = "123"

        val result = TraceTagComputer.getTag(className, methodName)
        val expect = "...23"

        assert(result == expect)
    }

}
package com.test.app

import android.util.Log

class Test {

    fun test() {
        Log.d("test", "test")
    }

    fun testException() {
        try {
            Log.d("test", "testException try begin")
            1 / 0
            Log.d("test", "testException try end")
        } catch (e: java.lang.Exception) {
            Log.d("test", "testException catch exception", e)
        }
    }

    fun testExceptionWithRethrow() {
        try {
            Log.d("test", "testException try begin")
            1 / 0
            Log.d("test", "testException try end")
        } catch (e: java.lang.Exception) {
            Log.d("test", "testException catch exception", e)
            throw e
        }
    }

    fun testReturn(): Int {
        return 0
    }

}
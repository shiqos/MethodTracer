package com.test.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.test_view).setOnClickListener {
            test()
        }
    }

    private fun test() {
        delayOne()
        delayTwo()
    }

    private fun delayOne() {
        Thread.sleep(1000)
        delayTwo()
    }

    private fun delayTwo() {
        Thread.sleep(2000)
    }
}
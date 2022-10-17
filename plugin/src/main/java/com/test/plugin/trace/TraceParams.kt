package com.test.plugin.trace

import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import javax.inject.Inject

open class TraceParams : InstrumentationParameters {

    @get:Input
    val ignoreClass: MutableSet<String> = mutableSetOf()

}
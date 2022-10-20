package com.test.plugin.trace

import com.android.build.api.instrumentation.InstrumentationParameters
import com.test.plugin.trace.internal.filter.TraceClassFilter
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

interface TraceParams : InstrumentationParameters {

    @get:Input
    val classFilter: Property<TraceClassFilter>

}
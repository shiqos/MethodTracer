package com.test.plugin.trace

import com.android.build.api.instrumentation.InstrumentationParameters
import com.test.plugin.trace.internal.Matcher
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import javax.inject.Inject

interface TraceParams : InstrumentationParameters {

    @get:Input
    val matcher: Property<Matcher>

}
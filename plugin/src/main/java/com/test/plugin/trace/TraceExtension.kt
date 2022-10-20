package com.test.plugin.trace

import org.gradle.api.provider.ListProperty

abstract class TraceExtension {

    abstract val ignoreClass: ListProperty<String>

}
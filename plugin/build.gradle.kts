plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("java-gradle-plugin")
}

dependencies {
    implementation(libs.gradle.android)
    implementation(libs.gradle.kotlin)
    implementation(libs.asm.commons)
    implementation(libs.asm.tree)
    testImplementation(libs.junit)
}

gradlePlugin {
    plugins {
        create("tracer-app") {
            id = "tracer"
            // 实现这个插件的类的路径
            implementationClass = "com.test.plugin.trace.TracePlugin"
        }
    }
}
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("java-gradle-plugin")
    id("maven-publish")
}

dependencies {
    gradleApi()
    implementation(libs.gradle.android)
    implementation(libs.gradle.kotlin)
    implementation("com.android.tools:common:30.0.2")
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

val GROUP_ID = "com.test"
val ARTIFACT_ID = "trace-plugin"
val VERSION = "1.0.0"

afterEvaluate {
    // 生成源码jar文件
    val sourceJar = task<Jar>("sourceJar") {
        archiveClassifier.set("sources")
    }

    this.group = GROUP_ID
    this.version = VERSION

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])

                groupId = GROUP_ID
                artifactId = ARTIFACT_ID
                version = VERSION
                description = "trace"

                // 带源码发布
                artifact(sourceJar)

                val gav = "$groupId:$artifactId:$version"
                project.logger.lifecycle("May publish $gav => $description")
            }

            repositories {
                maven {
                    setUrl("http://172.24.17.168:8081/repository/maven-snapshots/")
                    credentials {
                        username = "username"
                        password = "password"
                    }
                    isAllowInsecureProtocol = true
                }
            }
        }
    }
}
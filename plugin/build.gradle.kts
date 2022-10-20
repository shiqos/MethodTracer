plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("java-gradle-plugin")
    id("maven-publish")
}

dependencies {
    gradleApi()
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

afterEvaluate {
    // 生成源码jar文件
    val sourceJar = task<Jar>("sourceJar") {
        archiveClassifier.set("sources")
    }

    this.group = "cn.futu"
    this.version = "1.0"

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])

                version = "1.0"
                groupId = "cn.futu"
                artifactId = "trace-plugin"
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
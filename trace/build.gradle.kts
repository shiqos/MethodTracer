plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.test.trace"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    testImplementation(libs.bundles.test)
}

val GROUP_ID = "com.test"
val ARTIFACT_ID = "trace-runtime"
val VERSION = "1.0.0"

afterEvaluate {
    // 生成源码jar文件
    val sourceJar = task<Jar>("sourceJar") {
        from(android.sourceSets.getByName("main").java.srcDirs)
        archiveClassifier.set("sources")
    }

    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

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
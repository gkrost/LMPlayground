import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.0"
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.druk.lmplayground"

    defaultConfig {
        applicationId = "com.druk.lmplayground"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1_01_01
        versionName = "1.1.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        externalNativeBuild {
            cmake {
                arguments += "-DLLAMA_CURL=OFF"
                arguments += "-DLLAMA_BUILD_COMMON=ON"
                arguments += "-DGGML_LLAMAFILE=OFF"
                cppFlags += "-std=c++11"
            }
        }

        ndk {
            abiFilters += setOf("arm64-v8a", "x86_64")
        }
    }

    ndkVersion = "27.2.12479018"

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    signingConfigs {
        val userKeystore = File(System.getProperty("user.home"), ".android/keystore.jks")
        val localKeystore = rootProject.file("debug.keystore")
        val hasKeyInfo = userKeystore.exists()
        named("debug") {
            storeFile = if (hasKeyInfo) userKeystore else localKeystore
            storePassword = if (hasKeyInfo) System.getenv("STORE_PASSWORD") else "android"
            keyAlias = if (hasKeyInfo) System.getenv("LM_PLAYGROUND_KEY_ALIAS") else "androiddebugkey"
            keyPassword = if (hasKeyInfo) System.getenv("LM_PLAYGROUND_KEY_PASSWORD") else "android"
        }
    }

    buildTypes {
        getByName("debug") {
            isJniDebuggable = false
        }

        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.10"
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("arm64-v8a", "x86_64")
            isUniversalApk = true
        }
    }

    testOptions {
        managedDevices {
            allDevices {
                maybeCreate<ManagedVirtualDevice>("mvdApi35").apply {
                    device = "Pixel"
                    apiLevel = 35
                    systemImageSource = "google"
                    require64Bit = true
                }
            }
        }
    }

    packaging.resources {
        excludes += "/META-INF/AL2.0"
        excludes += "/META-INF/LGPL2.1"
    }
}

// Add missing Material dependencies to resolve resource errors

dependencies {
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.compose.material3:material3:1.3.2")
}

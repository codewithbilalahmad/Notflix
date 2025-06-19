import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinX.serialization.plugin)
    id("com.google.osdetector") version "1.7.3"
    alias(libs.plugins.ksp)
    alias(libs.plugins.sqlDelight)

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")

    js {
        browser()
        binaries.executable()
    }
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(libs.sqlDelight.driver.android)
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.cio)
            implementation(libs.datastore.preferences)
            implementation(libs.datastore)
            implementation(libs.androidx.datastore.core)
        }
        commonMain.dependencies {
            implementation(libs.ktor.core)
            implementation(libs.ktor.logging)
            implementation(libs.coil.network.ktor)
            implementation(libs.ktor.content.negociation)
            implementation(libs.ktor.serialization.json)
            implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.5.0")
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.coroutines)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.kotlinX.dateTime)
            implementation(libs.navigation)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.bundles.kmpPalette)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.sqlite)
            val fxSuffix = when (osdetector.classifier) {
                "linux-x86_64" -> "linux"
                "linux-aarch_64" -> "linux-aarch64"
                "windows-x86_64" -> "win"
                "osx-x86_64" -> "mac"
                "osx-aarch_64" -> "mac-aarch64"
                else -> throw IllegalStateException("Unknown OS: ${osdetector.classifier}")
            }
            implementation("org.openjfx:javafx-base:19:${fxSuffix}")
            implementation("org.openjfx:javafx-graphics:19:${fxSuffix}")
            implementation("org.openjfx:javafx-controls:19:${fxSuffix}")
            implementation("org.openjfx:javafx-swing:19:${fxSuffix}")
            implementation("org.openjfx:javafx-web:19:${fxSuffix}")
            implementation("org.openjfx:javafx-media:19:${fxSuffix}")
            implementation(libs.androidx.datastore.preferences.core.jvm)
        }
        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.ktor.client.js)
            implementation(libs.sqlDelight.driver.js)

            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
        }
        iosMain.dependencies {
            implementation(libs.sqlDelight.driver.native)
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "org.muhammad.notflix"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.muhammad.notflix"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.muhammad.notflix.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.muhammad.notflix"
            packageVersion = "1.0.0"
        }
    }
}
sqldelight {
    databases {
        create("NotflixDatabase") {
            packageName.set("org.muhammad.notflix")
        }
    }
}

compose.experimental{
    web.application{}
}

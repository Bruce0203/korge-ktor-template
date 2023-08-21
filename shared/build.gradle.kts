import korlibs.korge.gradle.KorgeGradlePlugin
import korlibs.korge.gradle.korge
import korlibs.korge.gradle.typedresources.GenerateTypedResourcesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply<KorgeGradlePlugin>()
apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
plugins {
    kotlin("multiplatform")
}

korge {
    targetJvm()
    targetJs()
    targetDesktopCross()
    targetDesktop()
}

tasks.create<Delete>("disableKRes") {
    dependsOn(tasks.withType<GenerateTypedResourcesTask>())
    afterEvaluate { File(buildDir, "KR/KR.kt").delete() }
}

tasks.all {
    if (name.contains("mingwX64", ignoreCase = true)) {
        onlyIf { it.name == "compileKotlinMingwX64" }
    }
}

tasks.create<Delete>("disableBootstrap") {
    afterEvaluate { File(buildDir, "platforms/native-desktop/bootstrap.kt").delete() }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    shouldRunAfter(tasks.getByName("disableBootstrap"))
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.uuid)
                api(libs.kotlinx.serialization)
                api(libs.ktor.client.auth)
                api(libs.ktor.client.content.negotation)
                api(libs.ktor.serialization.kotlinx.protobuf)
            }
        }
    }
}


import korlibs.korge.gradle.*

apply<KorgeGradlePlugin>()
apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
plugins { kotlin("multiplatform") }

korge {
    id = "com.sample.clientserver"
    targetJvm()
    targetJs()
    targetDesktopCross()
}

dependencies {
    add("commonMainImplementation", project(":shared"))
    add("commonMainImplementation", "de.cketti.unicode:kotlin-codepoints-deluxe:0.6.1")
}

dependencies {
    add("commonMainApi", project(":deps"))
}

@Suppress("UnstableApiUsage")
tasks.withType<ProcessResources> {
    filesMatching("client.properties") {
        expand(properties)
    }
}

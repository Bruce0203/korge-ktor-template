pluginManagement { repositories {  mavenLocal(); mavenCentral(); google(); gradlePluginPortal()  }  }

plugins {
    id("com.soywiz.kproject.settings") version "0.2.7"
}

kproject("./deps")

include(":shared")
include(":client")
include(":server")
include(":sandbox")
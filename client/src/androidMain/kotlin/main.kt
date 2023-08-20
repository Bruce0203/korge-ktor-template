import io.ktor.client.engine.cio.*
import korlibs.io.file.std.resourcesVfs
import korlibs.io.lang.readProperties

class Main

suspend fun runMain() {
    main()
}
suspend fun main() {
    val clientProps = resourcesVfs["client.properties"].readProperties()
    currentUrl = clientProps["server"]!!
    version = clientProps["version"]!!
    engine = CIO
    startMain()
}
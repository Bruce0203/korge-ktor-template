import io.ktor.client.engine.cio.*
import korlibs.io.async.runBlockingNoSuspensions
import korlibs.io.file.std.resourcesVfs
import korlibs.io.lang.readProperties
import kotlinx.coroutines.withContext
import network.ClientEngineFactory
import network.URLProvider
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

class Main

fun runMain() = main()

fun main() {
    runBlockingNoSuspensions {
        val clientProps = resourcesVfs["client.properties"].readProperties()
        val url = clientProps["server"]!!
        val version = clientProps["version"]!!
        startKoin {}
        KoinPlatform.getKoin().loadModules(listOf(module {
            single(named("version")) { version }
            factory {
                object : URLProvider {
                    override val url: String get() = url
                }
            } bind URLProvider::class
            factory {
                object : ClientEngineFactory { override fun getEngine() = CIO }
            } bind ClientEngineFactory::class
        }))
        start()
    }
}
import event.ResizedEvent
import io.ktor.client.engine.*
import korlibs.image.font.WoffFont
import korlibs.image.font.readWoffFont
import korlibs.image.text.TextAlignment
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.Korge
import korlibs.korge.scene.Scene
import korlibs.korge.scene.SceneContainer
import korlibs.korge.scene.sceneContainer
import korlibs.korge.style.*
import korlibs.korge.ui.UIContainer
import korlibs.korge.ui.uiContainer
import korlibs.korge.view.*
import korlibs.math.geom.Anchor
import korlibs.math.geom.ScaleMode
import korlibs.math.geom.Size
import ui.mainView
import util.ColorPalette
import util.transform
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

typealias KtorEngine = HttpClientEngineFactory<HttpClientEngineConfig>

lateinit var currentUrl: String
lateinit var version: String
lateinit var engine: KtorEngine

val defaultStyle: ViewStyles.() -> Unit = {
    textFont = font
    textAlignment = TextAlignment.MIDDLE_CENTER
    textSize = 100f
    textColor = ColorPalette.text
}

lateinit var scene: SceneContainer
lateinit var screen: UIContainer
lateinit var globalCoroutineContext: CoroutineContext

lateinit var font: WoffFont
lateinit var boldFont: WoffFont
suspend fun startMain() {
    globalCoroutineContext = coroutineContext
    font = resourcesVfs["fonts/NanumSquareNeoTTF-dEb.woff"].readWoffFont()
    boldFont = resourcesVfs["fonts/NanumSquareNeoTTF-eHv.woff"].readWoffFont()
    Korge(
        windowSize = Size(960, 540),
//        title = "",
//        icon = "images/logo.png",
        scaleMode = ScaleMode.NO_SCALE,
        clipBorders = false,
        scaleAnchor = Anchor.TOP_LEFT,
        backgroundColor = ColorPalette.background
    ) {
        scene = sceneContainer()
        scene.changeTo({ MainScene() })
    }
}

class MainScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        screen = uiContainer(size) { styles(defaultStyle) }
        onStageResized { width, height ->
            screen.size(width, height)
            dispatch(ResizedEvent())
        }
        screen.container {
            text(version, textSize = 30f) {
            }.zIndex(100)
            zIndex(100)
        }.transform {
            val padding = 10
            positionY(screen.height - height - padding)
            positionX(padding*2)
        }
        mainView()
    }
}
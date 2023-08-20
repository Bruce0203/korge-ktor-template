import korlibs.image.bitmap.Bitmap
import korlibs.image.bitmap.BmpSlice
import korlibs.image.bitmap.resized
import korlibs.image.font.Font
import korlibs.image.font.WoffFont
import korlibs.image.font.readWoffFont
import korlibs.image.format.readBitmap
import korlibs.image.format.readBitmapSlice
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.Korge
import korlibs.korge.scene.SceneContainer
import korlibs.korge.scene.sceneContainer
import korlibs.korge.ui.UIContainer
import korlibs.korge.view.*
import korlibs.math.geom.Anchor
import korlibs.math.geom.ScaleMode
import korlibs.math.geom.Size
import util.ColorPalette
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

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

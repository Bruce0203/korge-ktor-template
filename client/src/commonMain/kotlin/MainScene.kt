import event.PacketEvent
import event.ResizedEvent
import korlibs.image.font.Font
import korlibs.image.text.TextAlignment
import korlibs.korge.scene.Scene
import korlibs.korge.style.*
import korlibs.korge.ui.uiContainer
import korlibs.korge.view.*
import network.ServerClosedPacket
import util.ColorPalette
import util.launchNow
import util.transform

val styler: ViewStyles.() -> Unit = {
    textFont = font
    textAlignment = TextAlignment.MIDDLE_CENTER
    textSize = 100f
    textColor = ColorPalette.text
}

class MainScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        screen = uiContainer(size)
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
    }
}
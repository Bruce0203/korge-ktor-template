package ui

import korlibs.korge.ui.uiText
import korlibs.korge.view.align.centerOn
import screen
import util.transform

fun mainView() {
    screen.uiText("Hello").transform { centerOn(screen) }
}
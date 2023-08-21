package network

import event.PacketEvent
import io.ktor.util.*
import korlibs.io.net.ws.WebSocketClient
import korlibs.korge.ui.uiContainer
import korlibs.time.DateTime
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import network.ServerPacket.*
import MainScene
import currentUrl
import event.DisconnectedEvent
import kotlinx.serialization.ExperimentalSerializationApi
import scene
import screen
import sessionUUID
import util.launchNow

private var _websocketClient: WebSocketClient? = null
private var websocketSemaphore = false

suspend inline fun <reified T> sendToServer(packet: Enum<*>, t: T) {
    val packetFrame = PacketFrame(packet.ordinal, sessionUUID, serialFormat.encodeToByteArray<T>(t))
    runCatching { websocketClient().send(serialFormat.encodeToByteArray(packetFrame)) }.also {
        if (it.isFailure) {
            it.getOrThrow()
            screen.dispatch(DisconnectedEvent())
        }
        it.getOrThrow()
    }
}

suspend fun websocketClient(): WebSocketClient {
    if (_websocketClient === null) {
        if (websocketSemaphore) throw AssertionError("blocked by semaphore")
        websocketSemaphore = true
        try { _websocketClient = newWebsocketClient() }
        finally { websocketSemaphore = false }
    }
    return _websocketClient!!
}
private suspend fun newWebsocketClient(): WebSocketClient {
    val before  = DateTime.now()
    println(currentUrl.httpToWs())
    WebSocketClient(currentUrl.httpToWs())
    .also { it.startWebSocket() }.also { println(DateTime.now() - before); return it }
}

suspend fun WebSocketClient.startWebSocket() {
    send(ProtoBuf.encodeToByteArray(sessionUUID))
    onBinaryMessage {
        val packetFrame = ProtoBuf.decodeFromByteArray<PacketFrame>(it)
        val serverPacket = ServerPacket.values()[packetFrame.type]
        val packetController = serverPacket(serverPacket)
        launchNow {
            val data = decode(packetFrame.data, packetController.typeInfo)!!
            packetController.invoke(data)
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun serverPacket(serverPacket: ServerPacket): PacketController<Any> = when(serverPacket) {
    CHAT -> packet<ChatPacket>()
    PLAYER_JOIN -> packet<PlayerJoinPacket>()
    PLAYER_LEAVE -> packet<PlayerLeavePacket>()
    SERVER_CLOSED -> packet<ServerClosedPacket>()
} as PacketController<Any>

private inline fun <reified T : Any> packet() = packet<T> { scene.dispatch(PacketEvent(it)) }

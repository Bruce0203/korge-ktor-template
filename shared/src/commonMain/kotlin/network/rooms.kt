package network

import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
data class ViewedRoom(
    val uuid: UUID,
    val name: String,
    val maxPlayers: Int,
    val curPlayers: Int,
)

@Serializable
enum class RoomMode { NORMAL, TIMED, HARDCORE }
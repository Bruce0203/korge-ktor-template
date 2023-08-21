package event

import korlibs.event.Event
import korlibs.event.EventType
import korlibs.event.TEvent

class DisconnectedEvent : Event(), TEvent<DisconnectedEvent> {
    companion object : EventType<DisconnectedEvent>
    override val type: EventType<DisconnectedEvent> get() = DisconnectedEvent

    override fun toString(): String = "DisconnectedEvent()"
}
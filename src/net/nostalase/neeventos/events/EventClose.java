package net.nostalase.neeventos.events;

import net.nostalase.neeventos.types.EventoObj;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventClose extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private EventoObj evento;

    public EventClose(EventoObj evento) {
        this.evento = evento;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}

package net.nostalase.neeventos.events;

import net.nostalase.neeventos.types.EventoObj;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventWin extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private EventoObj evento;
    private Player player;

    public EventWin(EventoObj evento, Player player) {
        this.evento = evento;
        this.player = player;
    }


    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}

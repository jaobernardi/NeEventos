package net.nostalase.neeventos.listeners;

import net.nostalase.neeventos.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EventListener implements Listener {
    private final Main plugin;

    public EventListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if (plugin.playersevents.containsKey(event.getEntity())){
            plugin.playersevents.get(event.getEntity()).players.remove(event.getEntity());
            plugin.playersevents.remove(event.getEntity());
            event.setKeepInventory(true);
        }
    }
}

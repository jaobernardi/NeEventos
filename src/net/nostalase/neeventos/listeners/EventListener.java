package net.nostalase.neeventos.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.nostalase.neeventos.Main;
import net.nostalase.neeventos.Utils.chat;
import net.nostalase.neeventos.events.EventStart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onEventStart(EventStart event){
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle( "§6O Um novo evento!", "§7o evento §6"+event.evento.name+"§7 começou", 5, 100, 5);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6Entre através de /eventos §l"+event.evento.name));

        }
    }
}

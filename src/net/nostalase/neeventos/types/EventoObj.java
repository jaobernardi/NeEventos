package net.nostalase.neeventos.types;

import com.mojang.brigadier.Command;
import net.nostalase.neeventos.Main;
import net.nostalase.neeventos.Utils.chat;
import net.nostalase.neeventos.Utils.utils;
import net.nostalase.neeventos.events.EventClose;
import net.nostalase.neeventos.events.EventStart;
import net.nostalase.neeventos.events.EventWin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

public class EventoObj {
    public BukkitCommand event_command;
    public String name;
    public String alias;
    public Player creator;
    public Location start_location;
    public Boolean open = false;
    public Boolean locked = false;
    public int max_players = -1;
    public Map<Player, Location> players = new HashMap<>();
    public List<Player> banned = new ArrayList<>();
    public Player winner;
    private final Main plugin;

    public EventoObj(Main plugin, Player creator, Location start_location, String name, int max_players) {
        this.max_players = max_players;
        this.plugin = plugin;
        this.creator = creator;
        this.start_location = start_location;
        this.alias = utils.removeSC(name).replace(" ", "").toLowerCase();
        plugin.events.put(alias, this);
    }

    public EventoObj(Main plugin, Player creator, Location start_location, String name) {
        this.plugin = plugin;
        this.name = name;
        this.creator = creator;
        this.alias = utils.removeSC(name).replace(" ", "").toLowerCase();
        this.start_location = start_location;
        System.out.println(this.alias);
        plugin.events.put(this.alias, this);
    }


    public void ingress(Player player){
        if (players.containsKey(player)) {
            chat.sendMessage("&cVocê já está neste evento.", player);
            return;
        }
        if (banned.contains(player)) {
            chat.sendMessage("&cVocê foi banido deste evento.", player);
            return;
        }
        if (max_players >= 0&&players.size()>=max_players){
            chat.sendMessage("&cEste evento está cheio.", player);
            return;
        }
        if (this.plugin.playersevents.containsKey(player)){
            this.plugin.playersevents.get(player).quit(player);
        }
        if (this.locked){
            chat.sendMessage("&cEste evento já está trancado.", player);
            return;
        }
        if (!this.open){
            chat.sendMessage("&cEste evento está fechado.", player);
            return;
        }
        this.plugin.playersevents.put(player, this);
        players.put(player, player.getLocation());
        if (max_players > 0&&players.size()>=max_players){
            this.locked = true;
            if (creator.isOnline()){chat.sendMessage("&cAVISO&7 - O evento foi trancado por estar cheio. mais players não poderão entrar, mesmo que players saiam do evento.", creator);}
        }
        player.teleport(start_location);
        for(Player pp: players.keySet())
            chat.sendMessage("&a+ "+player.getDisplayName()+" entrou no evento", pp);
    }

    public void quit(Player player){
        if (players.containsKey(player)){
            player.teleport(players.get(player));
            for(Player pp: players.keySet())
                chat.sendMessage("&c- "+player.getDisplayName()+" saiu do evento", pp);
            players.remove(player);
            chat.sendMessage("&cVocê saiu do evento.", player);
        }
    }


    public boolean openEvent() {
        EventStart eventStart = new EventStart(this);
        Bukkit.getPluginManager().callEvent(eventStart);
        this.open = !eventStart.isCancelled();
        return !eventStart.isCancelled();
    }

    public boolean closeEvent(){
        EventClose eventClose = new EventClose(this);
        Bukkit.getPluginManager().callEvent(eventClose);
        this.open = false;
        for (Player player : players.keySet()) {
            player.removeMetadata("currentevent", this.plugin);
            player.teleport(players.get(player));
            chat.sendMessage("&aO evento acabou! obrigado por participar :)", player);
            this.plugin.playersevents.remove(player);
        }
        this.plugin.events.remove(alias);
        players.clear();
        banned.clear();

        return true;
    }
    public void ToggleLock(){
       this.locked = !this.locked;
       if (creator.isOnline()){
           if (this.locked)
               chat.sendMessage("&cAVISO&7 - O evento foi trancado.", creator);
           else
             chat.sendMessage("&cAVISO&7 - O evento foi destrancado.", creator);
       }
    }
    public void setWinner(Player player){
        EventWin eventWin = new EventWin(this, player);
        this.open = false;
        player.sendTitle("", "§6§lVocê ganhou o evento", 10, 100, 5);
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100, 1);
        chat.sendMessage("&6Você ganhou o evento!", player);
        for (Player playera : players.keySet()) {
            chat.sendMessage("&a"+player.getDisplayName()+" ganhou o evento!", playera);
        }
        Bukkit.getPluginManager().callEvent(eventWin);
        closeEvent();
    }
}

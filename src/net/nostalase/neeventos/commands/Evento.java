package net.nostalase.neeventos.commands;

import net.nostalase.neeventos.Main;
import net.nostalase.neeventos.Utils.chat;
import net.nostalase.neeventos.types.EventoObj;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Evento implements CommandExecutor, TabCompleter {
    private Main plugin;
    public ArrayList<EventoObj> eventos = new ArrayList<>();

    public Evento(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Você não pode executar esse comando.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (player.hasPermission("neeventos.admin"))
                chat.sendMessage("&cUso correto /"+label+" (lista|edit|create|close)", player);
            else
                chat.sendMessage("&cUso correto /"+label+" (nome do evento | list)", player);
            return false;
        }

        if ("create".equals(args[0].toLowerCase())) {
            if(!player.hasPermission("eventos.manage.create")){
                chat.sendMessage("&cVocê não tem permissão.", player);
                return false;
            }
            if (args.length == 1) {
                chat.sendMessage("&cUso correto: /" + label + " create (nome do evento)", player);
                return false;
            }
            List<String> lisa =  new ArrayList<>(Arrays.asList(args));
            EventoObj evento = new EventoObj(this.plugin, player, player.getLocation(), String.join(" ", lisa.subList(1, args.length)));
            chat.sendMessage("Evento criado", player);
            return true;
        } else if (this.plugin.events.containsKey(args[0].toLowerCase())) {//Dealing with an real event
            EventoObj evtpoint = this.plugin.events.get(args[0].toLowerCase());

            Player target;
            if (args.length == 1){
                if (evtpoint.players.containsKey(player)) {
                    evtpoint.quit(player);
                    return false;
                }
                evtpoint.ingress(player);
                return false;
            }
            switch (args[1]){
                case "open":
                    if(!player.hasPermission("eventos.manage.open")){
                        chat.sendMessage("&cVocê não tem permissão.", player);
                        return false;
                    }
                    if(evtpoint.open){
                        chat.sendMessage("&cEste evento já está aberto.", player);
                        return false;
                    }
                    if(evtpoint.openEvent()){
                        chat.sendMessage("&aEste evento foi aberto.", player);
                        return true;
                    }
                    chat.sendMessage("&cEste evento não pode ser aberto.", player);
                    return false;

                case "close":
                    if(!player.hasPermission("eventos.manage.close")){
                        chat.sendMessage("&cVocê não tem permissão.", player);
                        return false;
                    }
                    if(!evtpoint.open){
                        chat.sendMessage("&cEste evento já está fechado.", player);
                        return false;
                    }
                    if(evtpoint.closeEvent()){
                        chat.sendMessage("&aEste evento foi fechado.", player);
                        return true;
                    }
                    chat.sendMessage("&cEste evento não pode ser fechado.", player);
                    return false;

                case "lock":
                    if(!player.hasPermission("eventos.manage.lock")){
                        chat.sendMessage("&cVocê não tem permissão.", player);
                        return false;
                    }
                    evtpoint.ToggleLock();
                    if(evtpoint.locked){
                        chat.sendMessage("&cEvento foi trancado.", player);
                        return true;
                    }
                    chat.sendMessage("&cEvento foi destrancado.", player);
                    return true;

                case "ban":
                    if (args.length < 3){
                        chat.sendMessage("&cUso correto: /eventos <nome> ban <player>", player);
                        return false;
                    }
                    if(!player.hasPermission("eventos.manage.ban")){
                        chat.sendMessage("&cVocê não tem permissão.", player);
                        return false;
                    }
                    target = Bukkit.getPlayer(args[2]);
                    if (target==null){
                        chat.sendMessage("&cPlayer não encontrado.", player);
                        return false;
                    }
                    if (!evtpoint.banned.contains(target)) {
                        evtpoint.quit(target);
                        evtpoint.banned.add(target);
                        chat.sendMessage("&cVocê foi banido deste evento", target);
                        chat.sendMessage("&aPlayer banido.", player);
                        return false;
                    }
                    evtpoint.banned.remove(target);
                    chat.sendMessage("&aPlayer desbanido.", player);
                    return false;

                case "win":
                    if (args.length < 3){
                        chat.sendMessage("&cUso correto: /eventos <nome> win <player>", player);
                        return false;
                    }
                    if(!player.hasPermission("eventos.manage.win")){
                        chat.sendMessage("&cVocê não tem permissão.", player);
                        return false;
                    }
                    target = Bukkit.getPlayer(args[2]);
                    if (target==null){
                        chat.sendMessage("&cPlayer não encontrado.", player);
                        return false;
                    }
                    if (evtpoint.players.containsKey(target)) {
                        evtpoint.setWinner(target);
                        chat.sendMessage("&aVencedor definido.", player);
                        return false;
                    }
                    chat.sendMessage("&cO player não está no evento.", player);
                    return false;

                case "kick":
                    if (args.length < 3){
                        chat.sendMessage("&cUso correto: /eventos <nome> kick <player>", player);
                        return false;
                    }
                    if(!player.hasPermission("eventos.manage.kick")){
                        chat.sendMessage("&cVocê não tem permissão.", player);
                        return false;
                    }
                    target = Bukkit.getPlayer(args[2]);
                    if (target==null){
                        chat.sendMessage("&cPlayer não encontrado.", player);
                        return false;
                    }
                    if (evtpoint.players.containsKey(target)) {
                        evtpoint.quit(target);
                        chat.sendMessage("&cVocê foi kickado deste evento", target);
                        chat.sendMessage("Player kickado.", player);
                        return false;
                    }
                    chat.sendMessage("&aPlayer não está no evento.", player);
                    return false;
                case "setspawn":
                    if(!player.hasPermission("eventos.manage.setspawn")){
                        chat.sendMessage("&cVocê não tem permissão.", player);
                        return false;
                    }
                    evtpoint.start_location = player.getLocation();
                    chat.sendMessage("Localização inicial alterada.", player);
                case "maxplayers":
                    if (args.length < 3){
                        chat.sendMessage("&cUso correto: /eventos <nome> maxplayers <número>", player);
                        return false;
                    }
                    if(!player.hasPermission("eventos.manage.maxplayers")){
                        chat.sendMessage("&cVocê não tem permissão.", player);
                        return false;
                    }
                    try{
                        evtpoint.max_players = new Integer(args[2]);
                        chat.sendMessage("Limite máximo de players alterada.", player);
                        return false;
                    } catch(java.lang.NumberFormatException exception){
                        chat.sendMessage("Valor inválido", player);
                        return false;
                    }
                default:
                    chat.sendMessage("/eventos <nome>", player);
                    if(player.hasPermission("eventos.manage"))
                        chat.sendMessage("/eventos <nome> open", player);
                        chat.sendMessage("/eventos <nome> close", player);
                        chat.sendMessage("/eventos <nome> lock", player);
                        chat.sendMessage("/eventos <nome> ban <player>", player);
                        chat.sendMessage("/eventos <nome> kick <player>", player);
                        chat.sendMessage("/eventos <nome> win <player>", player);
                        chat.sendMessage("/eventos <nome> setspawn", player);
                        chat.sendMessage("/eventos <nome> maxplayers <número>", player);
                    return true;

            }
        }
        chat.sendMessage("&cNão há nenhum evento com este nome.", player);


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equals("eventos")&&sender instanceof Player){
            Player player = (Player) sender;
            List<String> lista;
            switch (args.length) {
                case 1:
                    lista = new ArrayList<>(this.plugin.events.keySet());
                    if (player.hasPermission("eventos.manage.create"))
                        lista.add("create");
                    return lista;
                case 2:
                   lista = new ArrayList<>();
                    if (player.hasPermission("eventos.manage")&&!args[0].equals("create"))
                        lista.add("open");
                        lista.add("close");
                        lista.add("win");
                        lista.add("maxplayers");
                        lista.add("setspawn");
                        lista.add("kick");
                        lista.add("ban");
                        lista.add("lock");
                   return lista;
                case 3:
                    if(args[1].toLowerCase().equals("ban")||args[1].toLowerCase().equals("kick")||args[1].toLowerCase().equals("win")){
                        lista = new ArrayList<>();
                        for(Player name:Bukkit.getOnlinePlayers()){
                            lista.add(name.getName());
                        }
                        return lista;
                    }
            }
        }
        return null;
    }
}

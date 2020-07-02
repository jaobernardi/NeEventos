package net.nostalase.neeventos.commands;

import net.nostalase.neeventos.Main;
import net.nostalase.neeventos.Utils.chat;
import net.nostalase.neeventos.types.EventoObj;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Evento implements CommandExecutor {
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

        switch (args[0].toLowerCase()){
            case "list":
                if (eventos.size() == 0) {
                    chat.sendMessage("&7Não há eventos ativos.", player);
                    return true;
                }
                chat.sendMessage("&7Eventos ativos:", player);
                for (EventoObj evento : eventos) {
                    chat.sendMessage("&a - "+ evento.name, player);
                }
                return true;
            case "create":
                if (args.length == 1) {
                    chat.sendMessage("&cUso correto: /"+label+" create (nome do evento)", player);
                    return false;
                }
                EventoObj evento = new EventoObj(this.plugin, player, player.getLocation(), args[1]){};
                evento.name = args[1];
                evento.creator = player;

        }
        return false;
    }
}

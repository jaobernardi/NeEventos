package net.nostalase.neeventos.types;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class EventCommand extends BukkitCommand {

    private final EventoObj evento;

    public EventCommand (EventoObj evento, List<String> aliases) {
        super(evento.name);

        this.setAliases(aliases);
        this.evento = evento;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            return false;
        }
        Player player = (Player) commandSender;
        if (this.evento.players.containsKey(player))
            this.evento.quit((Player) commandSender);
        else
            this.evento.ingress((Player) commandSender);
        return true;
    }
}


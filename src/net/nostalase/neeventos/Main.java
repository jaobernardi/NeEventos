package net.nostalase.neeventos;

import net.nostalase.neeventos.commands.Evento;
import net.nostalase.neeventos.listeners.EventListener;
import net.nostalase.neeventos.types.EventoObj;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public Map<Player, EventoObj> playersevents = new HashMap<>();
    public Map<String, EventoObj> events = new HashMap<>();
    private Logger logger = this.getLogger();

    @Override
    public void onLoad() {
        logger.info("Carregado!");
        SetupConfig();
    }

    @Override
    public void onEnable() {
        logger.info("Ativado!");

        // Commands
        logger.info("Registrando comandos.");
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new EventListener(this), this);
        this.getCommand("eventos").setExecutor(new Evento(this));
        this.getCommand("eventos").setTabCompleter(new Evento(this));
    }

    public void SetupConfig() {
        if (!(new File(getDataFolder (), "config.yml").exists())) {
            this.saveDefaultConfig ();
        }
    }



}

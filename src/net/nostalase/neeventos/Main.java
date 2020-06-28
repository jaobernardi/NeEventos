package net.nostalase.neeventos;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

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

    }

    public void SetupConfig() {
        if (!(new File(getDataFolder (), "config.yml").exists())) {
            this.saveDefaultConfig ();
        }
    }

}

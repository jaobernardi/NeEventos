package net.nostalase.neeventos.types;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class Event {
    public static String name;
    public static Player starter;
    public static Location start_location;
    public static Boolean hasstarted;
    public static List<Player> players;
    public static List<Player> banned;
    public static Player winner;


    public boolean openEvent(){return false;}
    public boolean closeEvent(){return false;}
    public void setWinner(Player player){}
}

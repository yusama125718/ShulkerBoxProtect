package yusama125718.shulkerboxprotect;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public final class ShulkerBoxProtect extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {

    public static JavaPlugin shulkerp;
    public static int time;
    public static boolean system;

    @Override
    public void onEnable() {
        getCommand("shulkerp").setExecutor(new Command());
        new Event(this);
        shulkerp = this;
        Function.ReloadConfig();
        getServer().getPluginManager().registerEvents(this, this);      //Event用
    }
}

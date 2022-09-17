package yusama125718.shulkerboxprotect;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;
import static yusama125718.shulkerboxprotect.ShulkerBoxProtect.*;

public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args){
        if (!sender.hasPermission("shulkerp.op")){
            return true;
        }
        switch (args.length){
            case 0:
                sender.sendMessage("§9§l[ShulkerBoxProtect] §7/shulkerp on/off §rシステムをon/offします");
                sender.sendMessage("§9§l[ShulkerBoxProtect] §7/shulkerp time [時間(分)] §r保護が有効な時間を設定します");
                sender.sendMessage("§9§l[ShulkerBoxProtect] §r現在システムは"+ system + "です");
                return true;

            case 1:
                if (args[0].equals("on")){
                    if (system){
                        sender.sendMessage("§9§l[ShulkerBoxProtect] §rすでにonになってます");
                        return true;
                    }
                    system = true;
                    shulkerp.getConfig().set("system",system);
                    shulkerp.saveConfig();
                    sender.sendMessage("§9§l[ShulkerBoxProtect] §ronにしました");
                }
                else if (args[0].equals("off")){
                    if (!system){
                        sender.sendMessage("§9§l[ShulkerBoxProtect] §rすでにoffになってます");
                        return true;
                    }
                    system = false;
                    shulkerp.getConfig().set("system",system);
                    shulkerp.saveConfig();
                    sender.sendMessage("§9§l[ShulkerBoxProtect] §roffにしました");
                }
                else{
                    sender.sendMessage("§9§l[ShulkerBoxProtect] §7/shulkerpでコマンドを確認");
                    return true;
                }

            case 2:
                if (args[0].equals("time")){
                    boolean isNumeric = args[1].matches("-?\\d+");
                    if (!isNumeric){
                        sender.sendMessage("§9§l[ShulkerBoxProtect] §r時間が無効です");
                        return true;
                    }
                    time = parseInt(args[1]);
                    shulkerp.getConfig().set("time",time);
                    shulkerp.saveConfig();
                    sender.sendMessage("§9§l[ShulkerBoxProtect] §r設定しました");
                }
                else{
                    sender.sendMessage("§9§l[ShulkerBoxProtect] §7/shulkerpでコマンドを確認");
                    return true;
                }

            default:
                sender.sendMessage("§9§l[ShulkerBoxProtect] §7/shulkerpでコマンドを確認");
                return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!sender.hasPermission("shulkerp.op")){
            return null;
        }
        if(command.getName().equalsIgnoreCase("shulkerp")){
            if (args.length == 1){
                if (args[0].length() == 0){
                    return Arrays.asList("on", "off", "time");
                }
                else {
                    if ("on".startsWith(args[0]) && "off".startsWith(args[0])) {
                        return Arrays.asList("on", "off");
                    }
                    else if ("on".startsWith(args[0])) {
                        return Collections.singletonList("on");
                    }
                    else if ("off".startsWith(args[0])) {
                        return Collections.singletonList("off");
                    }
                    else if ("time".startsWith(args[0])) {
                        return Collections.singletonList("time");
                    }
                }
            }
        }
        return null;
    }
}

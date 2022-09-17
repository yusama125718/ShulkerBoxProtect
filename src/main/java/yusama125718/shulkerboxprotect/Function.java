package yusama125718.shulkerboxprotect;

import static yusama125718.shulkerboxprotect.ShulkerBoxProtect.*;

public class Function {
    public static void ReloadConfig(){
        shulkerp.saveDefaultConfig();
        time = shulkerp.getConfig().getInt("time");
        system = shulkerp.getConfig().getBoolean("system");
    }
}

package yusama125718.shulkerboxprotect;

import org.bukkit.NamespacedKey;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static yusama125718.shulkerboxprotect.ShulkerBoxProtect.*;

public class Event implements Listener {
    public Event(ShulkerBoxProtect plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void PlaceShulker(BlockPlaceEvent e) {         //シュルカー設置
        if (!system) return;
        if (e.getBlockPlaced().getState() instanceof ShulkerBox shulker) {
            shulker.getPersistentDataContainer().set(new NamespacedKey(shulkerp, "shulkerp"), PersistentDataType.STRING, e.getPlayer().getUniqueId().toString());
            shulker.getPersistentDataContainer().set(new NamespacedKey(shulkerp, "shulkerpDate"), PersistentDataType.STRING, LocalDateTime.now().toString());
            shulker.update();
            e.getPlayer().sendMessage("§9§l[ShulkerBoxProtect] §r保護しました。有効時間は" + time + "分です");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void Interact(PlayerInteractEvent e) {         //シュルカーopen
        if (!system || !e.getAction().isRightClick() || e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getState() instanceof ShulkerBox shulker){
            if (!shulker.getPersistentDataContainer().has(new NamespacedKey(shulkerp, "shulkerp"), PersistentDataType.STRING) || !shulker.getPersistentDataContainer().has(new NamespacedKey(shulkerp, "shulkerpDate"), PersistentDataType.STRING)) return;
            LocalDateTime d = null;
            String s = shulker.getPersistentDataContainer().get(new NamespacedKey(shulkerp, "shulkerpDate"), PersistentDataType.STRING);
            d = LocalDateTime.parse(s);
            if (ChronoUnit.MINUTES.between(d,LocalDateTime.now()) > time) return;
            if (!shulker.getPersistentDataContainer().get(new NamespacedKey(shulkerp, "shulkerp"), PersistentDataType.STRING).equals(e.getPlayer().getUniqueId().toString()) && !e.getPlayer().hasPermission("shulkerp.op")){
                e.getPlayer().sendMessage("§9§l[ShulkerBoxProtect] §rこのシュルカーボックスはロックされています");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void BreakShulker(BlockBreakEvent e){
        if (!system) return;
        if (e.getBlock().getState() instanceof ShulkerBox shulker){
            if (!shulker.getPersistentDataContainer().has(new NamespacedKey(shulkerp, "shulkerp"), PersistentDataType.STRING) || !shulker.getPersistentDataContainer().has(new NamespacedKey(shulkerp, "shulkerpDate"), PersistentDataType.STRING)) return;
            LocalDateTime d = null;
            String s = shulker.getPersistentDataContainer().get(new NamespacedKey(shulkerp, "shulkerpDate"), PersistentDataType.STRING);
            d = LocalDateTime.parse(s);
            if (ChronoUnit.MINUTES.between(d,LocalDateTime.now()) > time) return;
            if (!shulker.getPersistentDataContainer().get(new NamespacedKey(shulkerp, "shulkerp"), PersistentDataType.STRING).equals(e.getPlayer().getUniqueId().toString()) && !e.getPlayer().hasPermission("shulkerp.op")){
                e.getPlayer().sendMessage("§9§l[ShulkerBoxProtect] §rこのシュルカーボックスはロックされています");
                e.setCancelled(true);
                return;
            }
            if (e.getPlayer().getInventory().firstEmpty() == -1){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§9§l[ShulkerBoxProtect] §rインベントリが満杯のためキャンセルしました");
                return;
            }
            e.setDropItems(false);
            ItemStack item = new ItemStack(e.getBlock().getType());
            BlockStateMeta bsm = (BlockStateMeta) item.getItemMeta();
            ShulkerBox box = (ShulkerBox) bsm.getBlockState();
            box.getInventory().setContents(shulker.getInventory().getContents());
            bsm.setBlockState(box);
            box.update();
            item.setItemMeta(bsm);
            e.getPlayer().getInventory().addItem(item);
            e.getPlayer().sendMessage("§9§l[ShulkerBoxProtect] §r破壊しました");
        }
    }
}

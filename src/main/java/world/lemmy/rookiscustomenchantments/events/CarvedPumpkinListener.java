package world.lemmy.rookiscustomenchantments.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import world.lemmy.rookiscustomenchantments.RookisCustomEnchantments;

public class CarvedPumpkinListener implements Listener {
    RookisCustomEnchantments plugin;

    public CarvedPumpkinListener(RookisCustomEnchantments plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDestroysPumpkin(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (p.getInventory().getItemInMainHand().containsEnchantment(plugin.carvedPumpkinEnchantment)) {
            if (event.getBlock().getType() == Material.PUMPKIN) {
                event.setDropItems(false);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.CARVED_PUMPKIN));
            }
        }
    }
}

package world.lemmy.rookiscustomenchantments.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import world.lemmy.rookiscustomenchantments.RookisCustomEnchantments;

import java.util.Set;

public class CarvedPumpkinEnchantment extends Enchantment implements Listener {

    // TO DO
    // @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)

    public void onEnchantPopulateVillagerAcquire(VillagerAcquireTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        ItemStack result = recipe.getResult();
        if (result.getType() != Material.ENCHANTED_BOOK) return;
        if (!result.getEnchantments().containsKey(this)) {
            if(Math.random() < 0.25){
                result.addEnchantment(this, 1);
            }
        }
    }
    @EventHandler
    public void onPlayerDestroysPumpkin(BlockBreakEvent event){
        Player p = event.getPlayer();
        if(p.getInventory().getItemInMainHand().containsEnchantment(this)){
            if(event.getBlock().getType() == Material.PUMPKIN){
                event.setDropItems(false);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.CARVED_PUMPKIN));
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        item.addUnsafeEnchantment(this, 1);
        p.getInventory().addItem(item);
    }

    public CarvedPumpkinEnchantment(RookisCustomEnchantments plugin) {
        super(new NamespacedKey(plugin, "carved_pumpkin"));
    }
    @Override
    public @NotNull String getName() {
        return "PumpkinCarver";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 2;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        // Only axes
        return item.getType() == Material.BOOK ||  item.getType() == Material.ENCHANTED_BOOK || item.getType() == Material.WOODEN_AXE || item.getType() == Material.STONE_AXE || item.getType() == Material.IRON_AXE || item.getType() == Material.GOLDEN_AXE || item.getType() == Material.DIAMOND_AXE || item.getType() == Material.NETHERITE_AXE;
    }

    @Override
    public @NotNull Component displayName(int level) {
        return Component.text("Carved Pumpkin");
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.UNCOMMON;
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return null;
    }

    @Override
    public @NotNull String translationKey() {
        return "carved_pumpkin_enchantment";
    }
}

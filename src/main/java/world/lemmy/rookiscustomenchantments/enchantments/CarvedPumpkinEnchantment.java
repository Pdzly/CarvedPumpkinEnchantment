package world.lemmy.rookiscustomenchantments.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import world.lemmy.rookiscustomenchantments.RookisCustomEnchantments;
import world.lemmy.rookiscustomenchantments.enchantments.trades.TradeConfig;

import java.util.Set;

public class CarvedPumpkinEnchantment extends CustomEnchantment {

    public CarvedPumpkinEnchantment(RookisCustomEnchantments plugin) {
        super(NamespacedKey.minecraft("carved_pumpkin"));

        TradeConfig trConfig = new TradeConfig();

        trConfig.setTradeChance(0.25);
        trConfig.setTradeCosts(30);
        trConfig.setTradeCostsMax(60);

        this.setTradeConfig(trConfig);
    }
    @Override
    public @NotNull String getName() {
        return "Pumpkin Carver";
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
        return Component.text(this.returnEnchantmentName(level));
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

package world.lemmy.rookiscustomenchantments.events;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import world.lemmy.rookiscustomenchantments.RookisCustomEnchantments;
import world.lemmy.rookiscustomenchantments.enchantments.CustomEnchantment;
import world.lemmy.rookiscustomenchantments.enchantments.trades.TradeConfig;

import javax.annotation.Nullable;
import java.util.List;

public class EnchantmentListener implements Listener {
    RookisCustomEnchantments plugin;

    public EnchantmentListener(RookisCustomEnchantments plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCombine(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.ANVIL) {
            AnvilInventory anvil = (AnvilInventory) event.getInventory();
            if (event.getSlot() == 2) {
                ItemStack item = event.getInventory().getItem(2);
                if (item == null) {
                    return;
                }

                ItemStack original = anvil.getItem(0);
                ItemStack combining = anvil.getItem(1);
                if (original == null || combining == null) {
                    return;
                }

                for (CustomEnchantment ench : plugin.customEnchantments) {
                    if (original.containsEnchantment(ench) || combining.containsEnchantment(ench)) {

                        int originalLevel = original.getEnchantmentLevel(ench);
                        int combiningLevel = combining.getEnchantmentLevel(ench);

                        int newLevel = Math.max(originalLevel, combiningLevel);

                        item.addUnsafeEnchantment(ench, 1);

                        item.lore(returnEnchantLore(item.lore(), ench, newLevel));

                    }
                }

            }
        }
    }

    @EventHandler
    public void onPlayerDisenchant(PrepareGrindstoneEvent event) {
        ItemStack result = event.getResult();
        ItemStack item = event.getInventory().getUpperItem();

        if (result == null || item == null) {
            return;
        }

        for (CustomEnchantment ench : plugin.customEnchantments) {
            if (item.containsEnchantment(ench)) {
                if (ench.isSticky()) {
                    result.addUnsafeEnchantment(ench, result.getEnchantmentLevel(ench));
                    continue;
                }
                result.removeEnchantment(ench);
                List<Component> lore = result.lore();
                if (lore != null) {
                    lore.removeIf(component -> component.toString().contains(ench.returnEnchantmentName(item.getEnchantmentLevel(ench))));
                }


                result.lore(lore);
            }
        }
    }

    @EventHandler
    public void onVillagerAquiredTrade(VillagerAcquireTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        ItemStack result = recipe.getResult();

        CustomEnchantment ench = null;

        for (CustomEnchantment customEnchantment : plugin.customEnchantments) {
            TradeConfig config = customEnchantment.getTradeConfig();
            if (customEnchantment.canEnchantItem(result) && config.getTradeChance() != -1 && config.getTradeChance() >= Math.random()) {
                ench = customEnchantment;
                break;
            }
        }
        if (ench == null) return;

        int level = (int) Math.floor(Math.random() * ench.getMaxLevel()) + 1;

        result.addUnsafeEnchantment(ench, level);

        result.lore(returnEnchantLore(result.lore(), ench, level));

        MerchantRecipe newRecipe = new MerchantRecipe(result, 5);

        TradeConfig cfg = ench.getTradeConfig();
        newRecipe.setIngredients(cfg.getIngredient());

        event.setRecipe(newRecipe);
    }

    public static List<Component> returnEnchantLore(@Nullable List<Component> oldLore, CustomEnchantment ench, int level) {

        if (oldLore == null) {
            oldLore = List.of(Component.text(ench.returnEnchantmentName(level)));
        } else {
            oldLore.add(Component.text(ench.returnEnchantmentName(level)));
        }
        return oldLore;
    }
}

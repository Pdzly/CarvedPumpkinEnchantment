package world.lemmy.rookiscustomenchantments.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import world.lemmy.rookiscustomenchantments.RookisCustomEnchantments;

import java.util.Arrays;
import java.util.List;

public class CarvedPumpkinGiver implements CommandExecutor {

    private RookisCustomEnchantments plugin;

    public CarvedPumpkinGiver(RookisCustomEnchantments plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player p) {
            Material itemInHand = p.getInventory().getItemInMainHand().getType();
            if (itemInHand == Material.AIR) {
                p.sendMessage(Component.text("You need to be holding an item to enchant!"));
                return true;
            }


            // Only axes
            if (!itemInHand.equals(Material.WOODEN_AXE)
                    && !itemInHand.equals(Material.STONE_AXE)
                    && !itemInHand.equals(Material.IRON_AXE)
                    && !itemInHand.equals(Material.DIAMOND_AXE)
                    && !itemInHand.equals(Material.GOLDEN_AXE)
                    && !itemInHand.equals(Material.NETHERITE_AXE)) {
                p.sendMessage(Component.text("You can only enchant axes with this enchantment!"));
                return true;
            }


            boolean hasBook = Arrays.stream(p.getInventory().getContents()).anyMatch(itemStack -> itemStack != null && itemStack.getType() == Material.BOOK);
            boolean hasEmeralds = Arrays.stream(p.getInventory().getContents()).anyMatch(itemStack -> itemStack != null && itemStack.getType() == Material.EMERALD && itemStack.getAmount() >= 30);

            if (!hasBook || !hasEmeralds) {
                p.sendMessage(Component.text("You need a book and 30 emeralds to get the Carved Pumpkin Enchantment!"));
                return true;
            }

            ItemStack item = p.getInventory().getItemInMainHand();
            item.addUnsafeEnchantment(plugin.carvedPumpkinEnchantment, 1);
            List<Component> lore = item.lore();
            if (lore == null)
                item.lore(List.of(Component.text(plugin.carvedPumpkinEnchantment.returnEnchantmentName(1))));
            else
                item.lore().add(Component.text(plugin.carvedPumpkinEnchantment.returnEnchantmentName(1)));

            p.getInventory().removeItem(new ItemStack(Material.BOOK, 1));
            p.getInventory().removeItem(new ItemStack(Material.EMERALD, 30));
            p.getInventory().setItemInMainHand(item);
            p.sendMessage(Component.text("You have successfully enchanted your item with " + plugin.carvedPumpkinEnchantment.returnEnchantmentName(1) + "!"));
        }

        return true;
    }
}

package world.lemmy.rookiscustomenchantments.enchantments.trades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TradeConfig {

    private double tradeChance = -1;

    private double tradeCosts = 5;

    private double tradeCostsMax = -1;

    private @Nullable ItemStack sideItem = null;

    public double getTradeChance() {
        return tradeChance;
    }

    public void setTradeChance(double tradeChance) {
        this.tradeChance = tradeChance;
    }

    public double getTradeCosts() {
        return tradeCosts;
    }

    public void setTradeCosts(double tradeCosts) {
        this.tradeCosts = tradeCosts;
    }

    public @Nullable ItemStack getSideItem() {
        return sideItem;
    }

    public void setSideItem(@Nullable ItemStack sideItem) {
        this.sideItem = sideItem;
    }

    public double getTradeCostsMax() {
        return tradeCostsMax;
    }

    public void setTradeCostsMax(double tradeCostsMax) {
        this.tradeCostsMax = tradeCostsMax;
    }

    public ArrayList<ItemStack> getIngredient() {
        ArrayList<ItemStack> ingredients = new ArrayList<>();
        if (sideItem != null) ingredients.add(sideItem);

        ItemStack emeralds = new ItemStack(Material.EMERALD, (int) Math.floor((tradeCostsMax != -1 ? tradeCostsMax : 1) * Math.random()) + (int) tradeCosts);
        ingredients.add(emeralds);

        return ingredients;
    }
}

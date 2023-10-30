package world.lemmy.rookiscustomenchantments.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;
import world.lemmy.rookiscustomenchantments.enchantments.trades.TradeConfig;

public abstract class CustomEnchantment extends Enchantment {
    private static final String[] NUMERALS = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};
    private boolean sticky = false;

    private TradeConfig tradeConfig = new TradeConfig();

    public CustomEnchantment(@NotNull NamespacedKey key) {
        super(key);
    }

    public TradeConfig getTradeConfig() {
        return tradeConfig;
    }

    public void setTradeConfig(TradeConfig tradeConfig) {
        this.tradeConfig = tradeConfig;
    }

    @Override
    public boolean isTradeable() {
        return tradeConfig.getTradeChance() != -1;
    }

    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public String returnEnchantmentName(int enchLevel) {
        if (enchLevel == 1 && this.getMaxLevel() == 1) {
            return this.getName();
        }
        if (enchLevel > 12 || enchLevel <= 0) {
            return this.getName() + " enchantment.level." + enchLevel;
        }

        return this.getName() + " " + NUMERALS[enchLevel - 1];
    }

}

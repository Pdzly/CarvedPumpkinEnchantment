package world.lemmy.rookiscustomenchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import world.lemmy.rookiscustomenchantments.enchantments.CarvedPumpkinEnchantment;

import java.lang.reflect.Field;
import java.util.HashMap;

public final class RookisCustomEnchantments extends JavaPlugin {

    CarvedPumpkinEnchantment carvedPumpkinEnchantment = new CarvedPumpkinEnchantment(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        RookisCustomEnchantments.registerEnchantment(carvedPumpkinEnchantment, this);

        getServer().getPluginManager().registerEvents(carvedPumpkinEnchantment, this);

    }

    public static void registerEnchantment(Enchantment enchantment, RookisCustomEnchantments plugin){
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if(registered){
            // It's been registered!
            plugin.getLogger().info(enchantment.getName() + " has been registered!");
        }
    }

    @Override
    public void onDisable() {
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

                    byKey.remove(carvedPumpkinEnchantment.getKey());

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

                    byName.remove(carvedPumpkinEnchantment.getName());
        } catch (Exception ignored) { }

    }
}

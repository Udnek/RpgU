package me.udnek.rpgu.mechanic.enchanting.upgrade;

import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.itemscoreu.customregistry.CustomRegistry;
import me.udnek.itemscoreu.customregistry.MappedCustomRegistry;
import me.udnek.itemscoreu.customregistry.Registrable;
import me.udnek.rpgu.RpgU;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface EnchantingTableUpgrade extends Registrable{

    CustomRegistry<EnchantingTableUpgrade> REGISTRY = CustomRegistries.addRegistry(
            RpgU.getInstance(), new MappedCustomRegistry<>("enchanting_table_upgrade"));

    EnchantingTableUpgrade LITTLE_BOOKSHELF = register(new BookShelfUpgrade("little_bookshelf", 5));
    EnchantingTableUpgrade DECENT_BOOKSHELF = register(new BookShelfUpgrade("decent_bookshelf", 10));
    EnchantingTableUpgrade LOTS_OF_BOOKSHELF = register(new BookShelfUpgrade("lots_of_bookshelf", 15));
    EnchantingTableUpgrade SCULK = register(new BlockUpgrade("sculk", Material.SCULK_SHRIEKER));
    EnchantingTableUpgrade TRIAL_CHAMBER = register(new BlockUpgrade("trial_chamber", Material.HEAVY_CORE));
    EnchantingTableUpgrade AMETHYST = register(new BlockUpgrade("amethyst", Material.AMETHYST_CLUSTER));
    EnchantingTableUpgrade END = register(new BlockUpgrade("end", Material.END_ROD));
    EnchantingTableUpgrade WATER = register(new BlockUpgrade("water", Material.CONDUIT));
    EnchantingTableUpgrade NETHER = register(new BlockUpgrade("nether", Material.ANCIENT_DEBRIS));

    static EnchantingTableUpgrade register(EnchantingTableUpgrade upgrade){
        return REGISTRY.register(RpgU.getInstance(), upgrade);
    }

    @NotNull ItemStack getIcon();

    boolean test(@NotNull Location tableLocation);
}

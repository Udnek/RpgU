package me.udnek.rpgu;

import me.udnek.itemscoreu.customentity.CustomDumbTickingEntity;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.damaging.DamageListener;
import me.udnek.rpgu.electricity.ElectricityEvents;
import me.udnek.rpgu.enchanting.EnchantmentTableListener;
import me.udnek.rpgu.enchanting.enchantment.Enchantments;
import me.udnek.rpgu.entity.Entities;
import me.udnek.rpgu.equipment.EquipmentListener;
import me.udnek.rpgu.equipment.PlayerWearingEquipmentTask;
import me.udnek.rpgu.item.Items;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public final class RpgU extends JavaPlugin {

    private static RpgU instance;
    private PlayerWearingEquipmentTask wearingEquipmentTask;

    public static RpgU getInstance() { return instance; }

/*    public static void changeRegistryLock(boolean isLocked) {
        MappedRegistry<Attribute> materials = getRegistry(Registries.ATTRIBUTE);
        try {
            Class<?> registryMaterialsClass = Class.forName("net.minecraft.core.RegistryMaterials");
            for (Field field : registryMaterialsClass.getDeclaredFields()) {
                if (field.getType() == boolean.class) {
                    field.setAccessible(true);
                    field.setBoolean(materials, isLocked);
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T> MappedRegistry<T> getRegistry(ResourceKey<Registry<T>> key) {
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        return (MappedRegistry<T>) server.registryAccess().registryOrThrow(key);
    }*/

    @Override
    public void onEnable() {
        instance = this;


        // FORCE INIT
        //Enchantment test = Enchantments.test;
        CustomItem blazeBlade = Items.blazeBlade;
        CustomDumbTickingEntity customDumbTickingEntity = Entities.energyVault;



        new DamageListener(this);
        new EquipmentListener(this);
        new ElectricityEvents(this);
        new EnchantmentTableListener(this);

        wearingEquipmentTask = new PlayerWearingEquipmentTask();
        wearingEquipmentTask.start(this);

    }

    @Override
    public void onDisable() {

        wearingEquipmentTask.stop();
    }
}
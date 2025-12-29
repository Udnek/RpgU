package me.udnek.rpgu.item;

import io.papermc.paper.datacomponent.item.Equippable;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.rpgu.RpgU;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractArmor extends ConstructableCustomItem {

    private final Material material;
    private final String id;
    private final String skinName;
    private final String engName;
    private final String ruName;

    public AbstractArmor(@NotNull Material material, @NotNull String id, @NotNull String skinName, @NotNull String engName, @NotNull String ruName) {
        this.material = material;
        this.id = id;
        this.skinName = skinName;
        this.engName = engName;
        this.ruName =  ruName;
    }

    @Override
    public @NotNull String getRawId() {return id;}
    @Override
    public @NotNull Material getMaterial() {return material;}
    @Override
    public @Nullable DataSupplier<Equippable> getEquippable() {
        return DataSupplier.of(Equippable.equippable(getMaterial().getEquipmentSlot()).assetId(new NamespacedKey(RpgU.getInstance(), skinName)).build());
    }
    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu(engName, ruName);}
}

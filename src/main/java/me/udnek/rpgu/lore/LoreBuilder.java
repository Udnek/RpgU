package me.udnek.rpgu.lore;

import me.udnek.rpgu.lore.slotdescription.*;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class LoreBuilder {

    private final ArrayList<Component> lore;

    public LoreBuilder(){
        lore = new ArrayList<>();
    }

    public void apply(ItemStack itemStack){
        LoreUtils.apply(itemStack, lore);
    }
    public void addEmptyLine() { lore.add(Component.empty()); }

    public void build(ItemStack itemStack){
        addEmptyLine();
        new HandDescription().generateAndAdd(lore, itemStack, true);

        new HeadDescription().generateAndAdd(lore, itemStack, true);
        new ChestDescription().generateAndAdd(lore, itemStack, true);
        new LegsDescription().generateAndAdd(lore, itemStack, true);
        new FeetDescription().generateAndAdd(lore, itemStack, true);

        apply(itemStack);
    }

}

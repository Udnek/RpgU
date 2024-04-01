package me.udnek.rpgu.lore;

import me.udnek.rpgu.lore.type.LoreArtifact;
import me.udnek.rpgu.lore.type.LoreMeele;
import me.udnek.rpgu.lore.type.LoreRanged;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LoreConstructor {

    private final ArrayList<Component> lore = new ArrayList<>();

    public LoreConstructor(){}

    public void apply(ItemStack itemStack){
        LoreUtils.apply(itemStack, lore);
    }
    public void apply(ItemMeta itemMeta){
        itemMeta.lore(lore);
    }

    public void add(Component component){
        lore.add(component);
    }

    public void addEmptyLine(){
        lore.add(Component.empty());
    }

    public ArrayList<Component> getLore(){
        return lore;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MELEE
    ///////////////////////////////////////////////////////////////////////////

    public void addMeeleLore(ItemStack itemStack){
        this.addEmptyLine();
        lore.addAll(LoreMeele.generateDefaultLore(itemStack));
    }

    public void addExtraMeeleInformation(Component component){
        lore.add(LoreMeele.toExtraInfoStyle(component));
    }

    public void addExtraMeeleInformation(List<Component> components){
        for (Component component : components) {
            lore.add(LoreMeele.toExtraInfoStyle(component));
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ARTIFACTS
    ///////////////////////////////////////////////////////////////////////////

    public void addArtifactTitle(){
        lore.add(LoreArtifact.whenEquippedAsArtifactLine());
    }

    public void addArtifactInformation(Component component){
        lore.add(LoreArtifact.toInfoStyle(component));
    }

    public void setArtifactInformation(List<Component> components){
        this.addEmptyLine();
        this.addArtifactTitle();
        this.addArtifactInformation(components);
    }

    public void addArtifactInformation(List<Component> components){
        for (Component component : components) {
            lore.add(LoreArtifact.toInfoStyle(component));
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // RANGED
    ///////////////////////////////////////////////////////////////////////////

    public void addRangedLore(ItemStack itemStack){
        this.addEmptyLine();
        lore.addAll(LoreRanged.generateDefaultLore(itemStack));
    }
}

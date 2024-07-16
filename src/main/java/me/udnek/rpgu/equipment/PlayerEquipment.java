package me.udnek.rpgu.equipment;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.abstraction.*;
import me.udnek.rpgu.mechanic.origin.Origin;

import java.util.EnumMap;

public class PlayerEquipment {

    private MainHandItem mainHand;
    private OffHandItem offHand;

    private ArmorItem head;
    private ArmorItem chest;
    private ArmorItem legs;
    private ArmorItem feet;

    private ArtifactItem artifactFirst;
    private ArtifactItem artifactSecond;
    private ArtifactItem artifactThird;

    private Origin origin;

    private EnumMap<Slot, Equippable> equippmentMap = new EnumMap<>(Slot.class);

    ///////////////////////////////////////////////////////////////////////////
    // GENERAL
    ///////////////////////////////////////////////////////////////////////////

    public EnumMap<Slot, Equippable> getFullEquipment(){return equippmentMap;}
    public boolean isEmpty() {return equippmentMap.isEmpty();}
    private void put(Slot slot, Equippable equippable){
        if (equippable == null){
            equippmentMap.remove(slot);
            return;
        }
        equippmentMap.put(slot, equippable);
    }

    public void setEquippableItem(EquippableItem equippableItem, CustomEquipmentSlot equipmentSlot, int slot){
        if (equippableItem != null){
            if (!equippableItem.isAppropriateSlot(equipmentSlot)) return;
        }

        setArtifact(slot, equippableItem);
        setArmor(slot, equippableItem);
        setOffHand(equippableItem);
    }

    ///////////////////////////////////////////////////////////////////////////
    // HANDS
    ///////////////////////////////////////////////////////////////////////////
    public void setMainHand(Equippable equippable){
        MainHandItem item;
        if (equippable instanceof MainHandItem) item = (MainHandItem) equippable;
        else item = null;

        this.mainHand = item;
        put(Slot.MAIN_HAND, item);
    }
    public MainHandItem getMainHand() {return mainHand;}

    public void setOffHand(Equippable equippable){
        OffHandItem item;
        if (equippable instanceof OffHandItem) item = (OffHandItem) equippable;
        else item = null;

        this.offHand = item;
        put(Slot.OFF_HAND, item);
    }
    public OffHandItem getOffHand() {return offHand;}

    ///////////////////////////////////////////////////////////////////////////
    // ORIGIN
    ///////////////////////////////////////////////////////////////////////////
    public void setOrigin(Origin origin){
        this.origin = origin;
        put(Slot.ORIGIN, origin);
    }
    public Origin getOrigin(){
        return origin;
    }

    ///////////////////////////////////////////////////////////////////////////
    // ARTIFACT
    ///////////////////////////////////////////////////////////////////////////
    public void setArtifact(int slot, Equippable equippable){
        ArtifactItem artifactItem;
        if (equippable instanceof ArtifactItem) artifactItem = (ArtifactItem) equippable;
        else artifactItem = null;
        switch (slot){
            case 9:
                this.artifactFirst = artifactItem;
                put(Slot.ARTIFACT_FIRST, artifactItem);
                break;
            case 10:
                this.artifactSecond = artifactItem;
                put(Slot.ARTIFACT_SECOND, artifactItem);
                break;
            case 11:
                this.artifactThird = artifactItem;
                put(Slot.ARTIFACT_THIRD, artifactItem);
                break;
        }
    }
    public boolean isEquippedAsArtifact(ArtifactItem customItem){
        if (this.artifactFirst == customItem) return true;
        if (this.artifactSecond == customItem) return true;
        if (this.artifactThird == customItem) return true;
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // ARMOR
    ///////////////////////////////////////////////////////////////////////////
    public void setArmor(int slot, Equippable equippable){
        ArmorItem armorItem;
        if (equippable instanceof ArmorItem) armorItem = (ArmorItem) equippable;
        else armorItem = null;

        switch (slot){
            case 39:
                this.head = armorItem;
                put(Slot.HEAD, armorItem);
                break;
            case 38:
                this.chest = armorItem;
                put(Slot.CHEST, armorItem);
                break;
            case 37:
                this.legs = armorItem;
                put(Slot.LEGS, armorItem);
                break;
            case 36:
                this.feet = armorItem;
                put(Slot.FEET, armorItem);
                break;
        }
    }
    public boolean isEquippedAsArmor(ArmorItem customItem){
        if (this.head == customItem) return true;
        if (this.chest == customItem) return true;
        if (this.legs == customItem) return true;
        if (this.feet == customItem) return true;
        return false;
    }

    public enum Slot{
        MAIN_HAND(CustomEquipmentSlots.MAIN_HAND),
        OFF_HAND(CustomEquipmentSlots.OFF_HAND),

        HEAD(CustomEquipmentSlots.HEAD),
        CHEST(CustomEquipmentSlots.CHEST),
        LEGS(CustomEquipmentSlots.LEGS),
        FEET(CustomEquipmentSlots.FEET),

        ARTIFACT_FIRST(EquipmentSlots.ARTIFACT),
        ARTIFACT_SECOND(EquipmentSlots.ARTIFACT),
        ARTIFACT_THIRD(EquipmentSlots.ARTIFACT),

        ORIGIN(EquipmentSlots.ORIGIN);

        public final CustomEquipmentSlot equipmentSlot;
        Slot(CustomEquipmentSlot slot){
            equipmentSlot = slot;
        }
    }
}

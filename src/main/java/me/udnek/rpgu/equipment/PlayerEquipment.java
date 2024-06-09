package me.udnek.rpgu.equipment;

import me.udnek.rpgu.item.abstraction.ArmorItem;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.item.abstraction.EquippableItem;

public class PlayerEquipment {

    private ArmorItem head;
    private ArmorItem chest;
    private ArmorItem legs;
    private ArmorItem feet;

    private ArtifactItem artifactFirst;
    private ArtifactItem artifactSecond;
    private ArtifactItem artifactThird;

    public void setArtifact(int slot, ArtifactItem artifactItem){
        switch (slot){
            case 9:
                this.artifactFirst = artifactItem;
                break;
            case 10:
                this.artifactSecond = artifactItem;
                break;
            case 11:
                this.artifactThird = artifactItem;
                break;
        }
    }
    public void setArmor(int slot, ArmorItem armorItem){
        switch (slot){
            case 39:
                this.head = armorItem;
                break;
            case 38:
                this.chest = armorItem;
                break;
            case 37:
                this.legs = armorItem;
                break;
            case 36:
                this.feet = armorItem;
                break;
        }
    }

    public EquippableItem[] getFullEquipment(){
        EquippableItem[] equippableItems = new EquippableItem[7];

        equippableItems[0] = this.head;
        equippableItems[1] = this.chest;
        equippableItems[2] = this.legs;
        equippableItems[3] = this.feet;

        equippableItems[4] = this.artifactFirst;
        equippableItems[5] = this.artifactSecond;
        equippableItems[6] = this.artifactThird;

        return equippableItems;
    }


    public boolean isEquippedAsArtifact(ArtifactItem customItem){
        if (this.artifactFirst == customItem) return true;
        if (this.artifactSecond == customItem) return true;
        if (this.artifactThird == customItem) return true;
        return false;
    }

    public boolean isEquippedAsArmor(ArmorItem customItem){
        if (this.head == customItem) return true;
        if (this.chest == customItem) return true;
        if (this.legs == customItem) return true;
        if (this.feet == customItem) return true;
        return false;
    }

    public ArmorItem getHead() {
        return this.head;
    }

    public void setHead(ArmorItem head) {
        this.head = head;
    }

    public ArmorItem getChest() {
        return this.chest;
    }

    public void setChest(ArmorItem chest) {
        this.chest = chest;
    }

    public ArmorItem getLegs() {
        return this.legs;
    }

    public void setLegs(ArmorItem legs) {
        this.legs = legs;
    }

    public ArmorItem getFeet() {
        return this.feet;
    }

    public void setFeet(ArmorItem feet) {
        this.feet = feet;
    }

    public ArtifactItem getArtifactFirst() {
        return this.artifactFirst;
    }

    public void setArtifactFirst(ArtifactItem artifactFirst) {
        this.artifactFirst = artifactFirst;
    }

    public ArtifactItem getArtifactSecond() {
        return this.artifactSecond;
    }

    public void setArtifactSecond(ArtifactItem artifactSecond) {this.artifactSecond = artifactSecond;}

    public ArtifactItem getArtifactThird() {
        return this.artifactThird;
    }

    public void setArtifactThird(ArtifactItem artifactThird) {
        this.artifactThird = artifactThird;
    }
}

package me.udnek.rpgu.equipment;

import me.udnek.rpgu.item.abstraction.ArmorItem;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.item.abstraction.EquippableItem;
import me.udnek.rpgu.item.abstraction.OriginItem;
import me.udnek.rpgu.origin.Origin;

public class PlayerEquipment {

    private ArmorItem head;
    private ArmorItem chest;
    private ArmorItem legs;
    private ArmorItem feet;

    private ArtifactItem artifactFirst;
    private ArtifactItem artifactSecond;
    private ArtifactItem artifactThird;

    private Origin origin;

    public void setOrigin(Origin origin){
        this.origin = origin;
    }
    public Origin getOrigin(){
        return origin;
    }

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

    public Equippable[] getFullEquipment(){
        Equippable[] equippables = new Equippable[8];

        equippables[0] = this.head;
        equippables[1] = this.chest;
        equippables[2] = this.legs;
        equippables[3] = this.feet;

        equippables[4] = this.artifactFirst;
        equippables[5] = this.artifactSecond;
        equippables[6] = this.artifactThird;

        equippables[7] = this.origin;

        return equippables;
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
    public ArtifactItem getArtifactFirst() {
        return this.artifactFirst;
    }
    public ArtifactItem getArtifactSecond() {
        return this.artifactSecond;
    }
    public ArtifactItem getArtifactThird() {
        return this.artifactThird;
    }

    public boolean hasAnyArtifact(){
        return !(artifactFirst == null && artifactSecond == null && artifactThird == null);
    }

/*    public ArmorItem getHead() {
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
    }*/
}

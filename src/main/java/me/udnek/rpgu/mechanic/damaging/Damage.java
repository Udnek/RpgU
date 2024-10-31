package me.udnek.rpgu.mechanic.damaging;

import org.jetbrains.annotations.NotNull;

public class Damage {

    private double physicalDamage = 0;
    private double magicalDamage = 0;

    public Damage(){}

    public Damage(Type type, double amount) {
        add(type, amount);
    }

    public Damage(double physicalDamage, double magicalDamage){
        addPhysical(physicalDamage);
        addMagical(magicalDamage);
    }

    public void nullify(){
        physicalDamage = 0;
        magicalDamage = 0;
    }

    public @NotNull Damage addPhysical(double amount){physicalDamage += amount; return this;}
    public @NotNull Damage addMagical(double amount){magicalDamage += amount; return this;}
    public @NotNull Damage add(@NotNull Type type, double amount){
        if (type == Type.PHYSICAL) return addPhysical(amount);
        return addMagical(amount);
    }
    public @NotNull Damage add(@NotNull Damage other){
        addPhysical(other.getPhysical());
        addMagical(other.getMagical());
        return this;
    }

    public @NotNull Damage multiply(double amount){return multiplyMagical(amount).multiplyPhysical(amount);}
    public @NotNull Damage multiply(@NotNull Type type, double amount){
        if (type == Type.PHYSICAL) return multiplyPhysical(amount);
        return multiplyMagical(amount);
    }
    public @NotNull Damage multiplyPhysical(double amount){physicalDamage *= amount; return this;}
    public @NotNull Damage multiplyMagical(double amount){magicalDamage *= amount; return this;}

    public double getPhysical() {return physicalDamage;}
    public double getMagical() {return magicalDamage;}

    public double getTotal() {return getPhysical() + getMagical();}



    @Override
    public String toString(){
        return "[Physical = " + getPhysical() + "; Magical = " + getMagical() + "; Sum = " + getTotal() + "]";
    }

    public enum Type {
        PHYSICAL,
        MAGICAL
    }

}

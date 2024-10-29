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

    public void add(@NotNull Type type, double amount){
        if (type == Type.PHYSICAL) addPhysical(amount);
        else addMagical(amount);
    }

    public void add(@NotNull Damage other){
        addPhysical(other.getPhysical());
        addMagical(other.getMagical());
    }

    public void addPhysical(double amount){physicalDamage += amount;}

    public void addMagical(double amount){magicalDamage += amount;}

    public void multiplyPhysical(double amount){physicalDamage *= amount;}

    public void multiplyMagical(double amount){magicalDamage *= amount;}

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

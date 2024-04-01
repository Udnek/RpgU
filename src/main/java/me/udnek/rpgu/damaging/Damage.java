package me.udnek.rpgu.damaging;

public class Damage {

    double physicalDamage = 0;
    double magicalDamage = 0;

    public Damage(){}

    public Damage(DamageType damageType, double amount) {
        this.addDamage(damageType, amount);
    }

    public Damage(double physicalDamage, double magicalDamage){
        this.addPhysicalDamage(physicalDamage);
        this.addMagicalDamage(magicalDamage);
    }


    public void addDamage(DamageType damageType, double amount){
        if (damageType == DamageType.PHYSICAL){
            this.addPhysicalDamage(amount);
            return;
        }
        this.addMagicalDamage(amount);
    }

    public void addDamage(Damage damage){
        this.addPhysicalDamage(damage.getPhysicalDamage());
        this.addMagicalDamage(damage.getMagicalDamage());
    }

    public void addPhysicalDamage(double amount){
        this.physicalDamage += amount;
    }

    public void addMagicalDamage(double amount){
        this.magicalDamage += amount;
    }

    public void multiplyPhysicalDamage(double amount){this.physicalDamage *= amount;}

    public void multiplyMagicalDamage(double amount){
        this.magicalDamage *= amount;
    }

    public double getPhysicalDamage() {
        return this.physicalDamage;
    }

    public double getMagicalDamage() {
        return this.magicalDamage;
    }

    public double getDamage() {return this.getPhysicalDamage() + this.getMagicalDamage();}


    public String toString(){
        return "[Physical = " + this.getPhysicalDamage() + "; Magical = " + this.getMagicalDamage() + "; Sum = " + this.getDamage() + "]";
    }

    public enum DamageType {
        PHYSICAL,
        MAGICAL
    }

}

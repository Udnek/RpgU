package me.udnek.rpgu.damaging;

public class Damage {

    private double physicalDamage = 0;
    private double magicalDamage = 0;

    public Damage(){}

    public Damage(Type type, double amount) {
        this.addDamage(type, amount);
    }

    public Damage(double physicalDamage, double magicalDamage){
        this.addPhysicalDamage(physicalDamage);
        this.addMagicalDamage(magicalDamage);
    }


    public void addDamage(Type type, double amount){
        if (type == Type.PHYSICAL){
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

    public double getTotalDamage() {return this.getPhysicalDamage() + this.getMagicalDamage();}


    public String toString(){
        return "[Physical = " + this.getPhysicalDamage() + "; Magical = " + this.getMagicalDamage() + "; Sum = " + this.getTotalDamage() + "]";
    }

    public enum Type {
        PHYSICAL,
        MAGICAL
    }

}

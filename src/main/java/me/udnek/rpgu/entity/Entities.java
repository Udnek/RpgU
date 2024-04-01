package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.CustomDumbTickingEntity;
import me.udnek.itemscoreu.customentity.CustomEntityManager;
import me.udnek.rpgu.RpgU;

public class Entities {

    public static final EnergyVaultEntity energyVault = (EnergyVaultEntity) register(new EnergyVaultEntity());

    private static CustomDumbTickingEntity register(CustomDumbTickingEntity customDumbTickingEntity){
        return CustomEntityManager.register(RpgU.getInstance(), customDumbTickingEntity);
    }
}

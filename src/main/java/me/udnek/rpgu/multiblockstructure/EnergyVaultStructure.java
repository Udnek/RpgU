package me.udnek.rpgu.multiblockstructure;

import me.udnek.itemscoreu.multiblockstructure.MultiBlockStructure;
import me.udnek.itemscoreu.multiblockstructure.blockchoice.AnyBlockChoice;
import me.udnek.itemscoreu.multiblockstructure.blockchoice.BlockChoice;
import me.udnek.itemscoreu.multiblockstructure.blockchoice.MaterialBlockChoice;
import me.udnek.rpgu.mechanic.electricity.EnergyVault;
import org.bukkit.Material;

public class EnergyVaultStructure{

    public static MultiBlockStructure create(){

        MultiBlockStructure.Builder builder = new MultiBlockStructure.Builder(3, 3, 3);

        MaterialBlockChoice cutCopper = new MaterialBlockChoice(Material.WAXED_CUT_COPPER);
        MaterialBlockChoice copper = new MaterialBlockChoice(Material.WAXED_COPPER_BLOCK);
        AnyBlockChoice any = new AnyBlockChoice();
        MaterialBlockChoice core = new MaterialBlockChoice(EnergyVault.CORE_BLOCK);

        builder.addLayer(new BlockChoice[][]
                {
                        {copper, copper, copper},
                        {copper, copper, copper},
                        {copper, copper, copper}
                }
        );
        builder.addLayer(new BlockChoice[][]
                {
                        {cutCopper, any, cutCopper},
                        {any,       core,      any},
                        {cutCopper, any, cutCopper},
                }
        );
        builder.addLayer(new BlockChoice[][]
                {
                        {copper, copper, copper},
                        {copper, copper, copper},
                        {copper, copper, copper}
                }
        );


        builder.setCenter(1, 1, 1);

        return builder.build();
    }


}

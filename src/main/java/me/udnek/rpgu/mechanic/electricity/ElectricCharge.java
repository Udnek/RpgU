package me.udnek.rpgu.mechanic.electricity;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.rpgu.RpgU;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.LightningRod;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ElectricCharge {

    protected Location currentLocation;
    //protected Location cameFrom;
    //protected BlockFace direction;
    protected float charge;
    protected static ParticleBuilder electricSpark;
    protected static ParticleBuilder smoke;

    static {
        ParticleBuilder electricSpark = new ParticleBuilder(Particle.ELECTRIC_SPARK);
        final double offset = 0;
        electricSpark.offset(offset, offset, offset);
        electricSpark.count(3);
        electricSpark.extra(0.5);

        ElectricCharge.electricSpark = electricSpark;

        ParticleBuilder smoke = new ParticleBuilder(Particle.CAMPFIRE_COSY_SMOKE);
        smoke.count(0);
        smoke.offset(0, 0.01, 0);

        ElectricCharge.smoke = smoke;
    }


    protected BlockFace getLightningRodFacing(Block block){
        return ((LightningRod) block.getBlockData()).getFacing();
    }
    protected BlockFace getLightningRodFacing(Location location){
        return getLightningRodFacing(location.getBlock());
    }


    public static void createChargeAtStruckLocation(Location struckLocation){
        ElectricCharge electricCharge = new ElectricCharge();
        electricCharge.currentLocation = struckLocation.clone().add(electricCharge.getFacingNextFromLightning(struckLocation).getDirection());
        electricCharge.start(100);
    }

    public static void createChargeAtFork(Location forkLocation, BlockFace direction, float charge){
        ElectricCharge electricCharge = new ElectricCharge();
        electricCharge.currentLocation = forkLocation.clone().add(direction.getDirection());
        electricCharge.start(charge);
    }

    public static void createChargeAtCore(Location coreLocation, float charge){
        ElectricCharge electricCharge = new ElectricCharge();
        electricCharge.currentLocation = coreLocation;
        electricCharge.start(charge);
    }

/*    private ElectricCharge(Location struckLightningRodLocation){
        //this.cameFrom = struckLightningRodLocation.clone();

        this.currentLocation = struckLightningRodLocation.clone().add(getFacingNextFromLightning(struckLightningRodLocation).getDirection());
    }

    private ElectricCharge(Location forkLocation, BlockFace suggestedDirection){
        ///this.cameFrom = forkLocation.clone();
        this.currentLocation = forkLocation.clone().add(suggestedDirection.getDirection());
    }*/


    protected BlockFace getFacingNextFromLightning(Location location){
        //Block block = cameFrom.getBlock();
        Block block = location.getBlock();
        return getLightningRodFacing(block);
        //direction = ((Directional) block.getBlockData()).getFacing().getOppositeFace();
    }

/*    protected boolean isPossibleNextBlock(Block block, BlockFace blockFace){
        switch (block.getType()) {
            case LIGHTNING_ROD:
                if (getLightningRodFacing(block).getOppositeFace() != blockFace) return true;
                return false;
            case WAXED_COPPER_BLOCK:
                if (currentLocation.getBlock().getType() == Material.LIGHTNING_ROD) return true;
                return false;
            default:
                return false;
        }
    }*/

    protected boolean isPossibleDirection(BlockFace blockFace){
        Block block = currentLocation.clone().add(blockFace.getDirection()).getBlock();
        switch (block.getType()) {
            case LIGHTNING_ROD:
                if (getLightningRodFacing(block) == blockFace) return true;
                return false;
            case WAXED_COPPER_BLOCK:
            case WAXED_CUT_COPPER:
                if (currentLocation.getBlock().getType() == Material.LIGHTNING_ROD) return true;
                return false;
            default:
                return false;
        }
    }


    protected void nextBlock(BlockFace blockFace){
        //cameFrom = currentLocation.clone();
        //direction = blockFace;
        currentLocation.add(blockFace.getDirection());
        playEffect();
    }

    protected List<BlockFace> getPossibleDirections(){
        ArrayList<BlockFace> faces = new ArrayList<>();
        Block block = currentLocation.getBlock();
        switch (block.getType()) {
            case WAXED_COPPER_BLOCK:
            case WAXED_CUT_COPPER:
            case BLAST_FURNACE:
                BlockFace[] copperBlockFaces = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
                for (BlockFace blockFace : copperBlockFaces) {
                    if (isPossibleDirection(blockFace)){
                        faces.add(blockFace);
                    }
                }
                break;
            case LIGHTNING_ROD:
                BlockFace blockFace = getLightningRodFacing(block);
                if (isPossibleDirection(blockFace)){
                    faces.add(blockFace);
                }
                break;
        }
        return faces;
    }

    public void start(float charge){
        this.charge = charge;
        playEffect();
        new Runner().runTaskTimer(RpgU.getInstance(), 0, 1);
    }

    protected boolean step(){

        if (charge <= 0) return false;

        List<BlockFace> possibleDirections = getPossibleDirections();
        if (possibleDirections.isEmpty()){
            return false;
        }

        charge --;

        if (possibleDirections.size() == 1){
            nextBlock(possibleDirections.get(0));
            return true;
        }
        for (int i = 1; i < possibleDirections.size(); i++) {
            ElectricCharge.createChargeAtFork(currentLocation, possibleDirections.get(i), charge);
        }
        nextBlock(possibleDirections.get(0));

        return true;
    }


    protected void playEffect(){
        electricSpark.location(currentLocation.toCenterLocation());
        electricSpark.spawn();
        smoke.location(currentLocation.toCenterLocation());
        smoke.spawn();

    }

    public class Runner extends BukkitRunnable{
        @Override
        public void run() {
            if (!step()) this.cancel();
        }
    }
}

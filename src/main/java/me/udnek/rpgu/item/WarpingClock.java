package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.utils.RightClickable;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class WarpingClock extends ConstructableCustomItem implements RightClickable, RpgUCustomItem {
    static int clockDuration = 20*5;
    //static NamespacedKey playerWarpingNamespace = new NamespacedKey(RpgU.getInstance(),"player_is_warping");

    static final HashMap<String, WarpingData> warpingDatabase = new HashMap<>();

    @Override
    public Integer getCustomModelData() {
        return 3102;
    }

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }

    @Override
    public String getRawId() {
        return "warping_clock";
    }

    private static void playBackParticles(Player player){
        //ParticleBuilder particleBuilder = new ParticleBuilder(Particle.SCULK_SOUL);
        //particleBuilder.location(player.getLocation().add(0, 1, 0));
        //particleBuilder.count(15);
        //particleBuilder.offset(0.35, 0.5, 0.3);
        //particleBuilder.extra(0.03);
        //particleBuilder.spawn();
        player.getLocation().getWorld().spawnParticle(Particle.SCULK_SOUL,
                player.getLocation().add(0,1,0),
                1,
                0.35, 0.5, 0.3, 0.03);
    }
    private static void playInvokeParticles(Player player){
        player.getLocation().getWorld().spawnParticle(Particle.SONIC_BOOM,
                player.getLocation().add(0, player.getEyeHeight(), 0).add(player.getEyeLocation().getDirection()),
                1,
                0d, 0d, 0d);
    }


    @Override
    public void onRightClicks(PlayerInteractEvent event) {
        boolean isWarping = getPlayerWarping(event.getPlayer());
        if (!isWarping) {
            playerStartWarping(event.getPlayer());
        }
        else{
            event.getPlayer().sendMessage("ALREADY WARPING");
        }
    }


    private static void playerStartWarping(Player player){
        playInvokeParticles(player);
        runLocationSaving(player);
    }

    private static void playerStoppedWarping(Player player){
        player.setFallDistance(0);
        playInvokeParticles(player);
    }

    private static void playerStartBacking(Player player){
        player.setFallDistance(0);
        PotionEffect speed = player.getPotionEffect(PotionEffectType.SPEED);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, clockDuration/2, (speed == null ? 4 : speed.getAmplifier()+4), true, false));
        playInvokeParticles(player);
    }


    private static void runLocationSaving(Player player){

        final int[] time = {WarpingClock.clockDuration};
        final Location[] locations = getPlayerWarpingLocations(player);
        WarpingClock.setPlayerWarping(player, true);

        setBackTask(player, Bukkit.getScheduler().runTaskTimer(RpgU.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (time[0] != 0)
                {
                    //Bukkit.getLogger().info(String.valueOf(time[0]));
                    locations[clockDuration - time[0]] = player.getLocation();
                    time[0]--;
                } else {
                    getBackTask(player).cancel();
                    playerStartBacking(player);
                    runWarpingBack(player);
                }
            }
        }, 0, 1));
    }

    private static void runWarpingBack(Player player){
        final int[] time = {WarpingClock.clockDuration};
        final Location[] locations = getPlayerWarpingLocations(player);

        setSavingTask(player, Bukkit.getScheduler().runTaskTimer(RpgU.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (time[0] != 0)
                {
                    //Bukkit.getLogger().info(String.valueOf(time[0]));
                    playBackParticles(player);
                    player.teleport(locations[(time[0]-1)]);
                    time[0]-=2;
                } else {
                    getSavingTask(player).cancel();
                    resetPlayerWarping(player);
                    playerStoppedWarping(player);
                }
            }
        }, 0, 1));
    }



    private static WarpingData getWarpingData(Player player){
        String uuid = player.getUniqueId().toString();
        if (warpingDatabase.containsKey(uuid)){
            return warpingDatabase.get(uuid);
        }
        WarpingData warpingData = new WarpingData();
        warpingDatabase.put(uuid, warpingData);
        return warpingData;
    }


    private static BukkitTask getSavingTask(Player player){
        return getWarpingData(player).getSavingTask();
    }
    private static void setSavingTask(Player player, BukkitTask savingTask){
        getWarpingData(player).setSavingTask(savingTask);
    }
    private static BukkitTask getBackTask(Player player){
        return getWarpingData(player).getBackTask();
    }
    private static void setBackTask(Player player, BukkitTask backTask){
        getWarpingData(player).setBackTask(backTask);
    }
    private static void setPlayerWarping(Player player, boolean isWarping){
        getWarpingData(player).setIsWarping(isWarping);
    }
    public static boolean getPlayerWarping(Player player){
        return getWarpingData(player).isWarping();
    }
    private static Location[] getPlayerWarpingLocations(Player player){
        return getWarpingData(player).getLocations();
    }
    private static void resetPlayerWarping(Player player){
        getWarpingData(player).reset();
    }



    private static class WarpingData{

        private boolean isWarping = false;
        private Location[] warpingLocations = new Location[clockDuration];
        private BukkitTask savingTask = null;
        private BukkitTask backTask = null;

        WarpingData(){}

        public boolean isWarping() {
            return this.isWarping;
        }

        public void setIsWarping(boolean isWarping) {
            this.isWarping = isWarping;
        }

        public Location[] getLocations(){
            return this.warpingLocations;
        }
        public void reset(){
            this.isWarping = false;
            this.warpingLocations = new Location[clockDuration];
            this.savingTask = null;
            this.backTask = null;
        }


        public BukkitTask getSavingTask() {
            return this.savingTask;
        }

        public void setSavingTask(BukkitTask savingTask) {
            this.savingTask = savingTask;
        }

        public BukkitTask getBackTask() {
            return this.backTask;
        }

        public void setBackTask(BukkitTask backTask) {
            this.backTask = backTask;
        }
    }
}

















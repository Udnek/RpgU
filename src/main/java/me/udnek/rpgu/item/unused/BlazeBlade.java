/*
package me.udnek.rpgu.item;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.DefaultCustomAttributeHolder;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.serializabledata.SerializableData;
import me.udnek.itemscoreu.serializabledata.SerializableDataManager;
import me.udnek.itemscoreu.util.LogUtils;
import me.udnek.itemscoreu.util.RightClickable;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.item.abstraction.MainHandItem;
import me.udnek.rpgu.lore.LoreUtils;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.particle.StunnedParticle;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BlazeBlade extends ConstructableCustomItem implements MainHandItem, RightClickable, DefaultCustomAttributeHolder {

    private final CustomAttributesContainer customAttributes =
            new CustomAttributesContainer.Builder()
                    .add(Attributes.MAGICAL_DAMAGE, 4, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.MAIN_HAND)
                    .build();

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_SWORD;
    }
    @Override
    public String getRawId() {
        return "blaze_blade";
    }
    @Override
    public Integer getCustomModelData() {
        return 3100;
    }

    @Override
    public ItemFlag[] getTooltipHides() {
        return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};
    }

    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.GENERIC_ATTACK_DAMAGE, null,-4);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }

    @Override
    public CustomAttributesContainer getDefaultCustomAttributes() {
        return customAttributes;
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, CustomEquipmentSlot slot, DamageEvent event) {

*/
/*        SaveData previousData = new SaveData();
        SerializableDataManager.read(previousData, RpgU.getInstance(), player);
        LogUtils.log(String.valueOf(previousData.getLocation()));

        SerializableDataManager.write(new SaveData(player.getLocation()), RpgU.getInstance(), player);

        if (previousData.getLocation() != null){
            player.teleport(previousData.getLocation());
        }*//*


        if (!(event.getVictim() instanceof LivingEntity victim)) return;

        Entity damager = event.getDamager();

        final boolean doBackBounce = !damager.isSneaking();
        final boolean victimIsPlayer = victim instanceof Player;
        new BukkitRunnable() {
            int i = 0;
            Vector previousVelocity = victim.getVelocity();
            Vector velocity;
            Location previousLocation = victim.getLocation();

            public void updateVelocity(){
                if (victimIsPlayer){
                    velocity = victim.getLocation().subtract(previousLocation).toVector();
                    previousLocation = victim.getLocation();
                }
                else {
                    velocity = victim.getVelocity();
                }
            }
            @Override
            public void run() {

                updateVelocity();

                if (i == 0) {
                    velocity.multiply(2).setY(0.4);
                    victim.setVelocity(velocity);
                }

                //LogUtils.log(velocity.toString());

*/
/*                if (i== 1){
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 17, 6, false, true, true));
                }*//*


                if ((velocity.getX() == 0 || velocity.getZ() == 0) && previousVelocity.length() >= 0.3){
                    final int duration = 20*3;

                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, duration, 4, false, true, true));
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, 0, false, true, true));
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration, 1, false, true, true));

                    if (doBackBounce) {
                        victim.setVelocity(
                                previousVelocity.normalize().multiply(-0.12 * horizontalDistance(damager.getLocation(), victim.getLocation())).setY(0.5)
                        );
                    }

                    ParticleBuilder builder = new ParticleBuilder(Particle.CRIT);
                    builder.count(25);
                    builder.offset(0.05, 0.05, 0.05);
                    builder.location(victim.getEyeLocation());
                    builder.spawn();
                    new StunnedParticle(victim.getEyeLocation(), victim).play();
                    //new BackstabParticle(victim.getEyeLocation()).play();

                    cancel();
                    return;
                }

                i++;
                if (i == 15) {
                    cancel();
                    return;
                }
                previousVelocity = velocity;

            }


        }.runTaskTimer(RpgU.getInstance(), (victimIsPlayer ? 2 : 1), 1);

    }

    public double horizontalDistance(Location from, Location to){
        return Math.sqrt(NumberConversions.square(from.getX() - to.getX()) + NumberConversions.square(from.getZ() - to.getZ()));
    }


    @Override
    public void onRightClicks(PlayerInteractEvent event) {
        // TODO: 2/11/2024 USAGE


*/
/*        event.getPlayer().sendMessage("CLICKED");
        NMSTest.sendEffectPacket(event.getPlayer());*//*


        //Entities.energyVault.spawn(event.getPlayer().getLocation());

        //Structures.energyVault.build(playerInteractEvent.getPlayer().getLocation());
*/
/*
        if (event.getClickedBlock() == null) return;

        boolean standingHere = Structures.energyVault.isStandingHere(event.getClickedBlock().getLocation());
        event.getPlayer().sendMessage(String.valueOf(standingHere));*//*


*/
/*        ItemStack itemStack1 = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = itemStack1.getItemMeta();
        //itemMeta.addEnchant(Enchantments.test, 1, true);
        itemStack1.setItemMeta(itemMeta);
        playerInteractEvent.getPlayer().getInventory().addItem(itemStack1);*//*


        //NMSTest.getAllFeatures(playerInteractEvent.getPlayer().getLocation());

        //playerInteractEvent.getPlayer().getInventory().addItem(CeremoniousDagger.getInstance().getItem());

*/
/*        Collection<Entity> nearbyEntities = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 5, 5, 5);
        for (Entity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof Player) continue;
            NMSTest.setCameraEntity(player, nearbyEntity);
        }*//*


        //NMSTest.sendExplosionPacket(player);
        //NMSTest.startRiptiding(player, 5);
        //player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, 1, false, true));

        //player.setVelocity(player.getVelocity().multiply(4));
*/
/*        SizedFireball fireball = player.launchProjectile(SmallFireball.class);
        fireball.setIsIncendiary(false);
        fireball.setDisplayItem(new ItemStack(Material.AIR));
        fireball.setYield(10);
        fireball.setVisualFire(false);
        fireball.setVelocity(fireball.getVelocity().multiply(0));
        fireball.setDirection(fireball.getDirection().multiply(0));

        player.*//*

        //fireball.setShooter(null);
        //player.damage(3, fireball);
        //player.setNoDamageTicks(0);
*/
/*        new EntityDamageByEntityEvent(fireball, player, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION,
                new HashMap<EntityDamageEvent.DamageModifier, Double>(),
                new HashMap<EntityDamageEvent.DamageModifier, Function<? super Double, Double>>());*//*

    }

    @Override
    protected List<Recipe> generateRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        ShapedRecipe recipe1 = new ShapedRecipe(getNewRecipeKey(), this.getItem());

        recipe1.shape("A","B","C");

        recipe1.setIngredient('A', Material.BLAZE_ROD);
        recipe1.setIngredient('B', Material.CONDUIT);
        recipe1.setIngredient('C', Material.NETHERITE_SCRAP);

        recipes.add(recipe1);
        return recipes;
    }


    public class SaveData implements SerializableData {

        private Location location = null;
        public SaveData(Location location){
            this.location = location;
        }
        public SaveData(){}

        public Location getLocation() {
            return location;
        }

        @Override
        public String serialize() {
            return location.getWorld().getName()+","+location.getX()+","+location.getY()+","+location.getZ()+","+location.getYaw()+","+location.getPitch();
        }

        @Override
        public void deserialize(String data) {
            if (data == null) return;
            String[] split = data.split(",");
            if (split.length != 6) return;
            World world = Bukkit.getWorld(split[0]);
            double x = Double.parseDouble(split[1]);
            double y = Double.parseDouble(split[2]);
            double z = Double.parseDouble(split[3]);
            float yaw = Float.parseFloat(split[4]);
            float pitch = Float.parseFloat(split[5]);
            if (world == null) return;
            location = new Location(world, x, y, z, yaw, pitch);
        }

        @Override
        public String getDataName() {
            return getId()+"_tp_location";
        }
    }
}
*/

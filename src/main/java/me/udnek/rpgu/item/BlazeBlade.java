package me.udnek.rpgu.item;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.itemscoreu.customitem.InteractableItem;
import me.udnek.itemscoreu.utils.LogUtils;
import me.udnek.itemscoreu.utils.NMS.NMSTest;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.attribute.FastAttributeContainer;
import me.udnek.rpgu.damaging.AttributeUtils;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.entity.Entities;
import me.udnek.rpgu.item.abstracts.WeaponItem;
import me.udnek.rpgu.item.abstracts.attributable.DefaultAttributeHolder;
import me.udnek.rpgu.lore.LoreBuilder;
import me.udnek.rpgu.lore.LoreConstructor;
import me.udnek.rpgu.lore.TranslationKeys;
import me.udnek.rpgu.particle.BackstabParticle;
import me.udnek.rpgu.particle.StunnedParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BlazeBlade extends CustomModelDataItem implements InteractableItem, DefaultAttributeHolder, WeaponItem {

    private FastAttributeContainer attributesInHand = new FastAttributeContainer(Attributes.magicalDamage, 6);

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_SWORD;
    }

    @Override
    protected String getRawDisplayName() {
        return TranslationKeys.itemPrefix + getItemName();
    }

    @Override
    protected String getItemName() {
        return "blaze_blade";
    }

    @Override
    public int getCustomModelData() {
        return 3100;
    }

    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        //itemMeta.addEnchant(Enchantments.test, 2, true);
    }

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        AttributeUtils.addDefaultAttributes(itemStack);
        AttributeUtils.addSuitableAttribute(itemStack, Attribute.GENERIC_ATTACK_DAMAGE, -7);
        AttributeUtils.appendAttribute(itemStack, Attribute.GENERIC_ARMOR, 4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST);

        LoreBuilder loreBuilder = new LoreBuilder();
        loreBuilder.build(itemStack);
/*        LoreConstructor loreConstructor = new LoreConstructor();
        loreConstructor.addMeeleLore(itemStack);
        loreConstructor.apply(itemStack);*/
    }

    @Override
    public FastAttributeContainer getItemInMainHandAttributes() {
        return attributesInHand;
    }

    @Override
    public void onEntityAttacks(LivingEntity entity, DamageEvent event) {

        if (!(event.getEvent().getEntity() instanceof LivingEntity)) return;

        LivingEntity victim = (LivingEntity) event.getEvent().getEntity();

        final boolean doBackBounce = !entity.isSneaking();
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
                    Vector extraVelocity = velocity.clone();
                    extraVelocity.multiply(2).setY(0.4);
                    victim.setVelocity(extraVelocity);
                    velocity = extraVelocity;
                }


                if ((velocity.getX() == 0 || velocity.getZ() == 0) && previousVelocity.length() >= 0.3){
                    final int duration = 20*3;

                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 5, false, true, true));
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, 0, false, true, true));
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration, 1, false, true, true));

                    if (doBackBounce) {
                        victim.setVelocity(
                                previousVelocity.normalize().multiply(-0.12 * horizontalDistance(entity.getLocation(), victim.getLocation())).setY(0.5)
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
                }
                i++;
                if (i == 15) cancel();
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


/*        event.getPlayer().sendMessage("CLICKED");
        NMSTest.sendEffectPacket(event.getPlayer());*/

        //Entities.energyVault.spawn(event.getPlayer().getLocation());

        //Structures.energyVault.build(playerInteractEvent.getPlayer().getLocation());
/*
        if (event.getClickedBlock() == null) return;

        boolean standingHere = Structures.energyVault.isStandingHere(event.getClickedBlock().getLocation());
        event.getPlayer().sendMessage(String.valueOf(standingHere));*/

/*        ItemStack itemStack1 = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = itemStack1.getItemMeta();
        //itemMeta.addEnchant(Enchantments.test, 1, true);
        itemStack1.setItemMeta(itemMeta);
        playerInteractEvent.getPlayer().getInventory().addItem(itemStack1);*/

        //NMSTest.getAllFeatures(playerInteractEvent.getPlayer().getLocation());

        //playerInteractEvent.getPlayer().getInventory().addItem(CeremoniousDagger.getInstance().getItem());

/*        Collection<Entity> nearbyEntities = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 5, 5, 5);
        for (Entity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof Player) continue;
            NMSTest.setCameraEntity(player, nearbyEntity);
        }*/

        //NMSTest.sendExplosionPacket(player);
        //NMSTest.startRiptiding(player, 5);
        //player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, 1, false, true));

        //player.setVelocity(player.getVelocity().multiply(4));
/*        SizedFireball fireball = player.launchProjectile(SmallFireball.class);
        fireball.setIsIncendiary(false);
        fireball.setDisplayItem(new ItemStack(Material.AIR));
        fireball.setYield(10);
        fireball.setVisualFire(false);
        fireball.setVelocity(fireball.getVelocity().multiply(0));
        fireball.setDirection(fireball.getDirection().multiply(0));

        player.*/
        //fireball.setShooter(null);
        //player.damage(3, fireball);
        //player.setNoDamageTicks(0);
/*        new EntityDamageByEntityEvent(fireball, player, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION,
                new HashMap<EntityDamageEvent.DamageModifier, Double>(),
                new HashMap<EntityDamageEvent.DamageModifier, Function<? super Double, Double>>());*/
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        ShapedRecipe recipe1 = new ShapedRecipe(this.getRecipeNamespace(), this.getItem());

        recipe1.shape("A","B","C");

        recipe1.setIngredient('A', Material.BLAZE_ROD);
        recipe1.setIngredient('B', Material.CONDUIT);
        recipe1.setIngredient('C', Material.NETHERITE_SCRAP);

        recipes.add(recipe1);
        return recipes;
    }
}

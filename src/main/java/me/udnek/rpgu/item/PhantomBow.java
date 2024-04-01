package me.udnek.rpgu.item;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.rpgu.lore.LoreConstructor;
import me.udnek.rpgu.lore.TranslationKeys;
import me.udnek.rpgu.particle.BowParticles;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class PhantomBow extends CustomModelDataItem {
    @Override
    public int getCustomModelData() {
        return 1000;
    }

    @Override
    public Material getMaterial() {
        return Material.BOW;
    }

    @Override
    protected String getRawDisplayName() {
        return TranslationKeys.itemPrefix + getItemName();
    }

    @Override
    protected String getItemName() {
        return "phantom_bow";
    }

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);

        //ProjectilePhysicalDamageAttribute.applyDefaultValue(itemStack);
        //ProjectileSpeedAttribute.applyDefaultValue(itemStack);

        LoreConstructor loreConstructor = new LoreConstructor();
        loreConstructor.addRangedLore(itemStack);

        loreConstructor.apply(itemStack);
    }

    @Override
    public void onShoots(EntityShootBowEvent event, ItemStack itemStack) {

        Entity projectile = event.getProjectile();
        if (!(projectile instanceof AbstractArrow)) return;
        AbstractArrow arrow = ((AbstractArrow) projectile);

        //CustomAttributes.set(new MagicalDamageAttribute(5D), arrow);

        arrow.setVelocity(arrow.getVelocity().multiply(1.5));
        arrow.setDamage(arrow.getDamage()/4);

        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.TOWN_AURA);
        particleBuilder.count(7);
        particleBuilder.extra(0);
        particleBuilder.offset(0.3, 0.3, 0.3);

        BowParticles.playParticleUntilGround(arrow, particleBuilder);

/*        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        player.sendMessage("PJAMTO");*/


    }
}

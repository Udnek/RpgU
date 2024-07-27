package me.udnek.rpgu.item;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.DefaultCustomAttributeHolder;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.itemscoreu.customevent.AllEventListener;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;
import me.udnek.rpgu.lore.LoreUtils;
import me.udnek.rpgu.particle.BowParticles;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class PhantomBow extends ConstructableCustomItem implements AllEventListener, DefaultCustomAttributeHolder, RpgUCustomItem {

    private CustomAttributesContainer container = new CustomAttributesContainer.Builder()
            .add(Attributes.PROJECTILE_SPEED, 0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, CustomEquipmentSlots.HAND)
            .add(Attributes.PROJECTILE_DAMAGE, -0.25, AttributeModifier.Operation.MULTIPLY_SCALAR_1, CustomEquipmentSlots.HAND)
            .build();

    @Override
    public Integer getCustomModelData() {
        return 1000;
    }

    @Override
    public Material getMaterial() {
        return Material.BOW;
    }

    @Override
    public String getRawId() {
        return "phantom_bow";
    }

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }

    @Override
    public CustomAttributesContainer getDefaultCustomAttributes() {
        return container;
    }

    @Override
    public void onEvent(Event event) {
        if (!(event instanceof EntityShootBowEvent shootEvent)) return;
        if (!isThisItem(shootEvent.getBow())) return;
        if (!(shootEvent.getProjectile() instanceof AbstractArrow arrow)) return;

        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.ASH);
        particleBuilder.count(7);
        particleBuilder.extra(0);
        particleBuilder.offset(0.3, 0.3, 0.3);

        BowParticles.playParticleUntilGround(arrow, particleBuilder);
    }
}

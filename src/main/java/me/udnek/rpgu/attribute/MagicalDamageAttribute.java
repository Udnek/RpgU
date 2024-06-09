package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.DefaultCustomAttributeHolder;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.LogUtils;
import me.udnek.rpgu.equipment.PlayersEquipmentDatabase;
import me.udnek.rpgu.item.abstraction.EquippableItem;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MagicalDamageAttribute extends TranslatableAttribute {

    @Override
    public String getRawId() {
        return "magical_damage";
    }

/*    public double getMeeleDamage(ItemStack itemStack){
        if (CustomItemUtils.isCustomItem(itemStack)){
            CustomItem customItem = CustomItemUtils.getFromItemStack(itemStack);
            if (customItem instanceof DefaultAttributeHolder){
                FastAttributeContainer itemInMainHandAttributes = ((DefaultAttributeHolder) customItem).getItemInMainHandAttributes();
                return itemInMainHandAttributes.getAttribute(Attributes.magicalDamage);
            }
            return 0;
        }
        else {
            return Attributes.magicalDamage.getDefaultValue(itemStack);
        }
    }*/

/*    public double calculateBaseEntityMagicalDamage(Entity entity){
        List<CustomAttributesContainer> containers = new ArrayList<>();
        if (entity instanceof Player player){

        }

        double amount = getDefaultValue();
        double multiplyBase = 1;
        double multiply = 1;
        for (CustomAttributesContainer container : containers) {
            for (Map.Entry<CustomAttribute, List<CustomAttributeModifier>> entry : container.getAll().entrySet()) {
                if (entry.getKey() != Attributes.MAGICAL_DAMAGE) continue;

                for (CustomAttributeModifier modifier : entry.getValue()) {
                    if (modifier.getOperation() == AttributeModifier.Operation.ADD_NUMBER) amount += modifier.getAmount();
                    else if (modifier.getOperation() == AttributeModifier.Operation.ADD_SCALAR) multiplyBase += modifier.getAmount();
                    else multiply *= (1 + modifier.getAmount());
                }
            }
        }

        amount *= multiplyBase;
        amount *= multiply;

        LogUtils.log("magicalDamage: "+ amount);

        return amount;
    }
    public double calculateBaseEntityMagicalDamage(Player player){
        EquippableItem[] playerEquipment = PlayersEquipmentDatabase.get(player).getFullEquipment();
        for (EquippableItem item : playerEquipment) {
            if (item == null) continue;
            CustomAttributesContainer customAttributes = item.getDefaultCustomAttributes();
            if (customAttributes != null) containers.add(customAttributes);
        }
        if (CustomItem.get(player.getInventory().getItemInMainHand()) instanceof DefaultCustomAttributeHolder holder){
            if (holder.getDefaultCustomAttributes() != null) containers.add(holder.getDefaultCustomAttributes());
        }
    }*/
}


















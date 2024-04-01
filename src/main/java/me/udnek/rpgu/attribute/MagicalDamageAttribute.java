package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttributeType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.CustomItemUtils;
import me.udnek.rpgu.item.abstracts.attributable.DefaultAttributeHolder;
import org.bukkit.inventory.ItemStack;

public class MagicalDamageAttribute extends TranslatableAttributeType {
    @Override
    public double getDefaultValue() {
        return 0;
    }

    @Override
    public String getName() {
        return "magical_damage";
    }

    public double getMeeleDamage(ItemStack itemStack){
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
    }
}

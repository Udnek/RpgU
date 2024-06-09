package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.Utils;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

import java.util.*;

public class VanillaAttributeContainer {

    private final HashMap<Attribute, List<CustomUUIDAttributeModifier>> attributes = new HashMap<>();

    private VanillaAttributeContainer(){}
    public static VanillaAttributeContainer empty(){return new VanillaAttributeContainer();}
    public List<CustomAttributeModifier> get(Attribute attribute){return new ArrayList<>(attributes.get(attribute));}
    public Map<Attribute, List<CustomUUIDAttributeModifier>> getAll(){return new HashMap<>(attributes);}
    public VanillaAttributeContainer get(CustomEquipmentSlot slot){
        VanillaAttributeContainer newContainer = new VanillaAttributeContainer();
        for (Map.Entry<Attribute, List<CustomUUIDAttributeModifier>> entry : attributes.entrySet()) {
            Attribute attribute = entry.getKey();
            for (CustomUUIDAttributeModifier modifier : entry.getValue()) {
                if (modifier.getEquipmentSlot() != slot) continue;
                newContainer.add(attribute, modifier);
            }

        }
        return newContainer;
    }
    public boolean contains(Attribute attribute){
        return attributes.containsKey(attribute);
    }
    private void add(Attribute attribute, CustomUUIDAttributeModifier modifier){
        List<CustomUUIDAttributeModifier> modifiers = attributes.get(attribute);
        if (modifiers == null){
            modifiers = new ArrayList<>();
            modifiers.add(modifier);
            attributes.put(attribute, modifiers);
            return;
        }
        modifiers.add(modifier);
    }

    public static class Builder{

        private VanillaAttributeContainer container;
        public Builder(){
            container = new VanillaAttributeContainer();
        }
        public VanillaAttributeContainer.Builder add(Attribute attribute, String seed, double amount, AttributeModifier.Operation operation, CustomEquipmentSlot slot){
            CustomUUIDAttributeModifier attributeModifier = new CustomUUIDAttributeModifier(Utils.UUIDFromSeed(seed), amount, operation, slot);
            return add(attribute, attributeModifier);
        }

        public VanillaAttributeContainer.Builder add(Attribute attribute, CustomUUIDAttributeModifier attributeModifier){
            container.add(attribute, attributeModifier);
            return this;
        }

        public VanillaAttributeContainer build(){
            return container;
        }

    }
}

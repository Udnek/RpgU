package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttributeType;

import java.util.HashMap;

public class FastAttributeContainer {

    HashMap<CustomAttributeType, Double> attributes = new HashMap<>();

    public FastAttributeContainer(){}

    public FastAttributeContainer(CustomAttributeType customAttributeType, double value){
        setAttributes(customAttributeType, value);
    }

    public double getAttribute(CustomAttributeType customAttributeType) {
        return attributes.getOrDefault(customAttributeType, customAttributeType.getDefaultValue());
    }

    public void setAttributes(CustomAttributeType customAttributeType, double value) {
        attributes.put(customAttributeType, value);
    }

    public HashMap<CustomAttributeType, Double> getAll(){
        return attributes;
    }

}

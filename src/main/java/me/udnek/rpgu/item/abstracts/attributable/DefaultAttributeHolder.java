package me.udnek.rpgu.item.abstracts.attributable;

import me.udnek.rpgu.attribute.FastAttributeContainer;

public interface DefaultAttributeHolder {

    default FastAttributeContainer getItemInMainHandAttributes(){return new FastAttributeContainer();}
    default FastAttributeContainer getItemInOffHandAttributes(){return new FastAttributeContainer();}

    default FastAttributeContainer getItemOnHeadAttributes(){return new FastAttributeContainer();}
    default FastAttributeContainer getItemOnChestAttributes(){return new FastAttributeContainer();}
    default FastAttributeContainer getItemOnLegsAttributes(){return new FastAttributeContainer();}
    default FastAttributeContainer getItemOnBootsAttributes(){return new FastAttributeContainer();}

    default FastAttributeContainer getItemInArtifactSlotAttributes(){return new FastAttributeContainer();}

}

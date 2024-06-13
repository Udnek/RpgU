package me.udnek.rpgu.hud;

import me.udnek.itemscoreu.utils.ComponentU;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

public class ArtifactImage {
    public final Component rawImage;
    public ArtifactImage(String key, int imageSize){
        int offset = (16-imageSize)/2;
        rawImage = ComponentU.textWithNoSpace(offset, Component.translatable(key), imageSize);
    }

    public static ArtifactImage defaultImage(){
        return new ArtifactImage("image.rpgu.artifact.unknown_artifact", 14);
    }
}

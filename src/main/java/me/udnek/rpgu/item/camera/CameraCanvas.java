package me.udnek.rpgu.item.camera;

import org.bukkit.map.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class CameraCanvas implements MapCanvas {

    private final MapView mapView;
    private final byte[][] pixels = new byte[128][128];
    private MapCursorCollection cursors;

    public CameraCanvas(MapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public @NotNull MapView getMapView() {
        return mapView;
    }

    @Override
    public @Nullable Color getPixelColor(int i, int i1) {
        return MapPalette.getColor(this.getPixel(i1, i1));
    }

    @Override
    public @NotNull Color getBasePixelColor(int i, int i1) {
        return MapPalette.getColor((byte) 0);
    }

    @Override
    public void setPixel(int i, int i1, byte b) {
        pixels[i][i1] = b;
    }

    @Override
    public void setPixelColor(int i, int i1, @Nullable Color color) {
        //setPixel(i, i1, MapPalette.to);
    }

    @Override
    public byte getPixel(int i, int i1) {
        return pixels[i][i1];
    }

    @Override
    public byte getBasePixel(int i, int i1) {
        return 0;
    }

    @Override
    public void drawImage(int i, int i1, @NotNull Image image) {
    }

    @Override
    public void drawText(int i, int i1, @NotNull MapFont mapFont, @NotNull String s) {
    }

    @Override
    public @NotNull MapCursorCollection getCursors() {
        return cursors == null ? cursors = new MapCursorCollection() : cursors;
    }

    @Override
    public void setCursors(@NotNull MapCursorCollection mapCursorCollection) {
        cursors = mapCursorCollection;
    }
}

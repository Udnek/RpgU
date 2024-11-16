package me.udnek.rpgu.util;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Utils {
    private Utils(){}

    public static <T extends Number> void consumeIfPositive(@NotNull T t, @NotNull Consumer<T> consumer){
        if (t.doubleValue() > 0) consumer.accept(t);
    }
}

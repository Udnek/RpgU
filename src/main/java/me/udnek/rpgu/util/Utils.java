package me.udnek.rpgu.util;

import java.util.function.Consumer;

public class Utils {
    private Utils(){}

    public static <T extends Number> void consumeIfPositive(T t, Consumer<T> consumer){
        if (t.doubleValue() >= 0) consumer.accept(t);
    }
}

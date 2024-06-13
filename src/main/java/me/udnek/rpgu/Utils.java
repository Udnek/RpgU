package me.udnek.rpgu;


import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.UUID;

public class Utils {

    public static String roundDoubleValueToTwoDigits(double value){
        if (value % 1 == 0){
            return String.valueOf(((int) value));
        }
        return new DecimalFormat("#.##").format(value);
    }
}

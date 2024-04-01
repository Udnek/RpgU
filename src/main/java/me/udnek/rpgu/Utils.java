package me.udnek.rpgu;


import java.text.DecimalFormat;

public class Utils {

    public static String roundDoubleValueToTwoDigits(double value){
        if (value % 1 == 0){
            return String.valueOf(((int) value));
        }
        return new DecimalFormat("#.##").format(value);
    }
}

package com.zhida.car.utils;

/**
 * Created by skylan on 16/12/25.
 */

public class IPUtils {
    public static String getip(String str) {
        int a = str.indexOf("=");
        int b = str.indexOf("=", a + 1);
        int c = str.indexOf("&", b + 1);
        String str1 = "";
        if (b + 1 == c) {
            int d = str.lastIndexOf("=");
            str1 = str.substring(d + 1);
        } else {
            str1 = str.substring(b + 1, c);
        }
        return str1;
    }
}

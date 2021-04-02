package com.natay.ecomm.bakery.utils;

/**
 * @author natayeung
 */
public class Arguments {

    public static void requireArgument(boolean expression, String reason) {
        if (!expression) {
            throw new IllegalArgumentException(reason);
        }
    }

    public static String requireNonBlank(String str, String reason) {
        requireNonNull(str, reason);

        if (str.isBlank()) {
            throw new IllegalArgumentException(reason);
        }

        return str;
    }

    public static <T> T requireNonNull(T obj, String reason) {
        if (obj == null) {
            throw new IllegalArgumentException(reason);
        }

        return obj;
    }

    private Arguments() {
    }
}

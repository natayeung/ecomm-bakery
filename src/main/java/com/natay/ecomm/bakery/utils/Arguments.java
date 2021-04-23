package com.natay.ecomm.bakery.utils;

import java.math.BigDecimal;
import java.util.List;

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

    public static <T> List<T> requireNonEmpty(List<T> list, String reason) {
        requireNonNull(list, reason);
        requireArgument(list.size() > 0, reason);
        return list;
    }

    public static BigDecimal requireNonNegative(BigDecimal number, String reason) {
        requireNonNull(number, reason);
        requireArgument(number.compareTo(BigDecimal.ZERO) >= 0, reason);
        return number;
    }

    private Arguments() {
    }
}

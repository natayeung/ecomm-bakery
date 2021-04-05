package com.natay.ecomm.bakery.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author natayeung
 */
public class RandomUtil {

    public static String randomEmail() {
        return RandomStringUtils.random(8, true, true) + "@gmail.com";
    }

    private RandomUtil() {
    }
}

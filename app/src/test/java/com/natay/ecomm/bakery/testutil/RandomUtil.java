package com.natay.ecomm.bakery.testutil;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author natayeung
 */
public class RandomUtil {

    public static String randomEmail() {
        return RandomStringUtils.random(8, true, true) + "@gmail.com";
    }

    public static String randomPassword() {
        return RandomStringUtils.random(4, true, true) + "pass";
    }

    private RandomUtil() {
    }
}

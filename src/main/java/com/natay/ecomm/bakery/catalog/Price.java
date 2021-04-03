package com.natay.ecomm.bakery.catalog;

import java.math.BigDecimal;
import java.util.Objects;

import static com.natay.ecomm.bakery.utils.Arguments.requireArgument;
import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

/**
 * @author natayeung
 */
public class Price {

    private final double value;

    private Price(double value) {
        this.value = value;
    }

    public static Price of(double value) {
        requireArgument(value >= 0.0, "Price value cannot be negative");
        requireArgument(new BigDecimal(String.valueOf(value)).scale() <= 2, "Price value cannot have more than 2 decimal places");

        return new Price(value);
    }

    public double value() {
        return value;
    }

    public Price add(Price theOther) {
        requireNonNull(theOther, "The other price must be specified");

        return Price.of(value + theOther.value);
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Double.compare(price.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
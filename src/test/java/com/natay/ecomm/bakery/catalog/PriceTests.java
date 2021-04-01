package com.natay.ecomm.bakery.catalog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class PriceTests {

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.50})
    public void mustBePositive(double value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Price.of(value));
    }

    @Test
    public void cannotHaveMoreThanTwoDecimalPlaces() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Price.of(2.495));
    }

    @ParameterizedTest
    @ValueSource(doubles = {2.49, 3.90, 5.2, 10})
    public void canBeConstructedWithTwoOrLessDecimalPlaces(double value) {
        Price constructed = Price.of(value);

        assertThat(constructed.value()).isEqualTo(value);
    }
}

package com.natay.ecomm.bakery.catalog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author natayeung
 */
public class PriceTests {

    @Test
    public void cannotBeNegative() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Price.of(-1.50));
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

    @ParameterizedTest
    @CsvSource({"2,2.00", "3.1,3.10", "4.25,4.25"})
    public void canBeInRepresentationOfTwoDecimalPlaces(double value, String expectedFormatted) {
        Price constructed = Price.of(value);

        assertThat(constructed.formattedValue()).isEqualTo(expectedFormatted);
    }

    @Test
    public void twoPricesCanBeAddedToGiveASum() {
        Price sum = Price.of(2.0).add(Price.of(11.73));

        assertThat(sum.value()).isEqualTo(13.73);
    }
}

package com.natay.ecomm.bakery.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author natayeung
 */
public class ArgumentsTests {

    @Test
    public void ensuresGivenExpressionEvaluatesToTrue() {
        int x = 5;

        assertThatCode(() -> Arguments.requireArgument(x > 0, "Cannot be negative"))
                .doesNotThrowAnyException();
    }

    @Test
    public void exceptionIsThrownWhenGivenExpressionEvaluatesToFalse() {
        int x = -1;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> Arguments.requireArgument(x > 0, "Cannot be negative"));
    }

    @Test
    public void ensuresGivenStringIsNotBlank() {
        String str = "not blank";

        assertThatCode(() -> Arguments.requireNonBlank(str, "Cannot be blank"))
                .doesNotThrowAnyException();
    }

    @Test
    public void exceptionIsThrownWhenGivenStringIsBlank() {
        String str = " ";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> Arguments.requireNonBlank(str, "Cannot be blank"));
    }

    @Test
    public void ensuresGivenObjectIsNotNull() {
        String obj = "an object";

        assertThatCode(() -> Arguments.requireNonNull(obj, "Cannot be null"))
                .doesNotThrowAnyException();
    }

    @Test
    public void exceptionIsThrownWhenGivenObjectIsNull() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Arguments.requireNonNull(null, "Cannot be null"));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1})
    public void ensuresGivenBigDecimalIsNotNegative(long value) {
        BigDecimal number = BigDecimal.valueOf(value);

        assertThatCode(() -> Arguments.requireNonNegative(number, "Cannot be negative"))
                .doesNotThrowAnyException();
    }

    @Test
    public void exceptionIsThrownWhenGivenBigDecimalIsNegative() {
        BigDecimal number = BigDecimal.valueOf(-1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> Arguments.requireNonNegative(number, "Cannot be negative"));
    }

    @Test
    public void ensuresGivenListIsNotEmpty() {
        List<String> list = List.of("Hello");

        assertThatCode(() -> Arguments.requireNonEmpty(list, "Cannot be empty"))
                .doesNotThrowAnyException();
    }

    @Test
    public void exceptionIsThrownWhenGivenListIsEmpty() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Arguments.requireNonEmpty(Collections.emptyList(), "Cannot be empty"));
    }
}
package com.natay.ecomm.bakery.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

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
}
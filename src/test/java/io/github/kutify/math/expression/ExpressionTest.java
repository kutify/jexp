package io.github.kutify.math.expression;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {

    @Test
    void test() {
        Expression expression = Expression.compile("(a/3)^2 + (b/3)^(-1)");

        assertEquals("29/18",
                expression.calculate(
                        Arguments.builder()
                                .with("a", 1)
                                .with("b", 2)
                                .build()
                ).toString()
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getPowTestArgs")
    void powerOperationTest(String expression, String expected) {
        String actual = String.valueOf(
                Expression.compile(expression)
                        .calculate(Arguments.EMPTY)
                        .doubleValue()
        );
        assertEquals(
                expected,
                actual.substring(0, expected.length())
        );
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> getPowTestArgs() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("(27)^(1/3)", "3.0"),
                org.junit.jupiter.params.provider.Arguments.of("(2)^(1/2)", "1.414")
        );
    }
}

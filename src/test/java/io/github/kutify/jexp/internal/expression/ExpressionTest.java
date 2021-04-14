package io.github.kutify.jexp.internal.expression;

import io.github.kutify.jexp.api.Expression;
import io.github.kutify.jexp.api.JExp;
import io.github.kutify.jexp.api.BigRational;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {

    static Stream<org.junit.jupiter.params.provider.Arguments> getPowTestArgs() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of("(27)^(1/3)", "3.0"),
            org.junit.jupiter.params.provider.Arguments.of("(27)^(1/3)", "3.0"),
            org.junit.jupiter.params.provider.Arguments.of("2 4 7", "56")
        );
    }

    @Test
    void test() {
        Expression<BigRational> expression = JExp.compileRational("(a/3)^2 + (b/3)^(-1)");

        assertEquals("29/18",
            expression.evaluate(
                JExp.rationalArgsBuilder()
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
            JExp.compileRational(expression)
                .evaluate(JExp.emptyArgs())
                .doubleValue()
        );
        assertEquals(
            expected,
            actual.substring(0, expected.length())
        );
    }
}

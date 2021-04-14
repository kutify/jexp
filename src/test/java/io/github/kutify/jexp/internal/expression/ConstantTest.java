package io.github.kutify.jexp.internal.expression;

import io.github.kutify.jexp.api.JExp;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantTest {

    private static Stream<Arguments> testData() {
        return Stream.of(
            Arguments.of("Pi", Math.PI),
            Arguments.of("E", Math.E)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void pi(String constantName, double expectedValue) {
        double actual = JExp.compile(constantName)
            .evaluate(JExp.emptyArgs());
        assertEquals(expectedValue, actual, Math.pow(10, -14));
    }
}

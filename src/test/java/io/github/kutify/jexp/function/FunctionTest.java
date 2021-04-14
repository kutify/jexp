package io.github.kutify.jexp.function;

import io.github.kutify.jexp.api.Expression;
import io.github.kutify.jexp.api.JExp;
import io.github.kutify.jexp.api.BigRational;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.github.kutify.jexp.function.FunctionTest.PrecisionType.ALL;
import static io.github.kutify.jexp.function.FunctionTest.PrecisionType.NONE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionTest {

    private static final double EPSILON = Math.pow(10, -14);

    private static Stream<Arguments> getTestData() {
        return Stream.of(
            Arguments.of("abs", "0", ALL, arr("0")),
            Arguments.of("abs", "1.5", ALL, arr("1.5")),
            Arguments.of("abs", "1.5", ALL, arr("-1.5")),

            Arguments.of("acos", "0", ALL, arr("1")),
            Arguments.of("acos", String.valueOf(Math.PI / 3), NONE, arr("0.5")),
            Arguments.of("acos", String.valueOf(Math.PI / 2), ALL, arr("0")),
            Arguments.of("acos", String.valueOf(Math.PI), NONE, arr("-1")),

            Arguments.of("asin", String.valueOf(Math.PI / 2), ALL, arr("1")),
            Arguments.of("asin", String.valueOf(Math.PI / 6), NONE, arr("0.5")),
            Arguments.of("asin", "0", ALL, arr("0")),
            Arguments.of("asin", String.valueOf(-Math.PI / 2), ALL, arr("-1")),

            Arguments.of("atan", String.valueOf(Math.PI / 4), ALL, arr("1")),
            Arguments.of("atan", "0", ALL, arr("0")),
            Arguments.of("atan", String.valueOf(-Math.PI / 4), ALL, arr("-1")),

//                Arguments.of("cbrt", "0", ALL, arr("0")),
//                Arguments.of("cbrt", "1", ALL, arr("1")),
//                Arguments.of("cbrt", "2", ALL, arr("8")),
//                Arguments.of("cbrt", "-2", ALL, arr("-8")),

            Arguments.of("cos", "1", ALL, arr("0")),
            Arguments.of("cos", "0.5", NONE, arr(String.valueOf(Math.PI / 3))),
            Arguments.of("cos", "0", NONE, arr(String.valueOf(Math.PI / 2))),
            Arguments.of("cos", "-1", ALL, arr(String.valueOf(Math.PI))),

            Arguments.of("exp", "1", ALL, arr("0")),
            Arguments.of("exp", String.valueOf(1 / Math.E), NONE, arr("-1")),
            Arguments.of("exp", String.valueOf(Math.E), NONE, arr("1")),
            Arguments.of("exp", String.valueOf(Math.pow(Math.E, 3)), NONE, arr("3")),

            Arguments.of("log", "0", ALL, arr("1")),
            Arguments.of("log", "-1", NONE, arr(String.valueOf(1 / Math.E))),
            Arguments.of("log", "1", NONE, arr(String.valueOf(Math.E))),
            Arguments.of("log", "3", NONE, arr(String.valueOf(Math.pow(Math.E, 3)))),

            Arguments.of("log10", "0", ALL, arr("1")),
            Arguments.of("log10", "-1", NONE, arr("0.1")),
            Arguments.of("log10", "1", NONE, arr("10")),
            Arguments.of("log10", "3", NONE, arr("1000")),

            Arguments.of("max", "10", ALL, arr("10")),
            Arguments.of("max", "15", ALL, arr("15", "5")),
            Arguments.of("max", "15", ALL, arr("15", "5")),
            Arguments.of("max", "25", ALL, arr("15", "25")),
            Arguments.of("max", "125", ALL, arr("15", "125", "50")),
            Arguments.of("max", "50", ALL, arr("15", "-125", "50")),

            Arguments.of("min", "10", ALL, arr("10")),
            Arguments.of("min", "5", ALL, arr("15", "5")),
            Arguments.of("min", "5", ALL, arr("15", "5")),
            Arguments.of("min", "15", ALL, arr("15", "25")),
            Arguments.of("min", "15", ALL, arr("15", "125", "50")),
            Arguments.of("min", "-125", ALL, arr("15", "-125", "50")),

            Arguments.of("mult", "0", ALL, arr("0")),
            Arguments.of("mult", "0", ALL, arr("0", "10")),
            Arguments.of("mult", "20", ALL, arr("2", "10")),
            Arguments.of("mult", "-20", ALL, arr("2", "-10")),
            Arguments.of("mult", "140", ALL, arr("2", "-10", "-7")),

            Arguments.of("signum", "0", ALL, arr("0")),
            Arguments.of("signum", "1", ALL, arr("1/2112312")),
            Arguments.of("signum", "1", ALL, arr("110")),
            Arguments.of("signum", "-1", ALL, arr("-23523")),
            Arguments.of("signum", "-1", ALL, arr("-1/322")),

            Arguments.of("sin", "0", ALL, arr("0")),
            Arguments.of("sin", "0.5", NONE, arr(String.valueOf(Math.PI / 6))),
            Arguments.of("sin", "1", NONE, arr(String.valueOf(Math.PI / 2))),
            Arguments.of("sin", "0", NONE, arr(String.valueOf(Math.PI))),

//                Arguments.of("sqrt", "0", NONE, arr(String.valueOf(Math.PI))),

            Arguments.of("sum", "0", ALL, arr("0")),
            Arguments.of("sum", "-4", ALL, arr("-4")),
            Arguments.of("sum", "2.3", ALL, arr("-4", "6.3")),
            Arguments.of("sum", "-7.7", ALL, arr("-4", "63/10", "-10")),

            Arguments.of("tan", "0", ALL, arr("0")),
            Arguments.of("tan", "1", NONE, arr(String.valueOf(Math.PI / 4))),
            Arguments.of("tan", "-1", NONE, arr(String.valueOf(3 * Math.PI / 4))),
            Arguments.of("tan", "0", NONE, arr(String.valueOf(Math.PI)))
        );
    }

    private static String[] arr(String... items) {
        return items;
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void doubleTest(String functionName, String expectedResult, PrecisionType precisionType, String... args) {
        Expression<Double> doubleExpression = JExp
            .compileDouble(functionName + "(" + String.join(",", args) + ")");
        double result = doubleExpression.evaluate(JExp.emptyArgs());
        if (precisionType == ALL) {
            assertEquals(Double.valueOf(expectedResult), result);
        } else {
            assertEquals(Double.parseDouble(expectedResult), result, EPSILON);
        }
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void rationalTest(String functionName, String expectedResult, PrecisionType precisionType, String... args) {
        Expression<BigRational> rationalExpression = JExp
            .compileRational(functionName + "(" + String.join(",", args) + ")");
        BigRational result = rationalExpression.evaluate(JExp.emptyArgs());
        if (precisionType == NONE) {
            assertEquals(BigRational.parse(expectedResult).doubleValue(), result.doubleValue(), EPSILON);
        } else {
            assertEquals(BigRational.parse(expectedResult), result);
        }
    }

    enum PrecisionType {
        ALL,
        NONE
    }
}

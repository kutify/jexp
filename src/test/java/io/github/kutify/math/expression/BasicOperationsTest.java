package io.github.kutify.math.expression;

import io.github.kutify.math.number.BigRational;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicOperationsTest {

    @Test
    void modDoubleOperationTest() {
        Expression<Double> exp = Expression.compileDouble("10 % 3");
        assertEquals(1.0, exp.evaluate(Arguments.EMPTY_DOUBLE));
    }

    @Test
    void modRationalOperationTest() {
        Expression<BigRational> exp = Expression.compileRational("(7/3) % 2");
        assertEquals(BigRational.parse("1/3"), exp.evaluate(Arguments.EMPTY_RATIONAL));
    }
}

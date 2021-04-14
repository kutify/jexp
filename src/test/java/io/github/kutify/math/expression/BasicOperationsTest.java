package io.github.kutify.math.expression;

import io.github.kutify.math.api.Expression;
import io.github.kutify.math.api.JExp;
import io.github.kutify.math.number.BigRational;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicOperationsTest {

    @Test
    void modDoubleOperationTest() {
        Expression<Double> exp = JExp.compileDouble("10 % 3");
        assertEquals(1.0, exp.evaluate(JExp.emptyArgs()));
    }

    @Test
    void modRationalOperationTest() {
        Expression<BigRational> exp = JExp.compileRational("(7/3) % 2");
        assertEquals(BigRational.parse("1/3"), exp.evaluate(JExp.emptyArgs()));
    }
}

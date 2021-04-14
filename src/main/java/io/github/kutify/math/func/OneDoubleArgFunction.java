package io.github.kutify.math.func;

import io.github.kutify.math.number.BigRational;

abstract class OneDoubleArgFunction extends OneArgFunction {

    @Override
    BigRational applySingle(BigRational argValue) {
        double doubleResult = applySingle(argValue.doubleValue());
        if (Double.isInfinite(doubleResult) || Double.isNaN(doubleResult)) {
            throw new ArithmeticException();
        }
        return BigRational.valueOf(doubleResult);
    }
}

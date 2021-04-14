package io.github.kutify.jexp.internal.func;

import io.github.kutify.jexp.api.BigRational;

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

package io.github.kutify.math.func;

import io.github.kutify.math.number.BigRational;

public class Abs extends OneArgFunction {

    @Override
    public String getName() {
        return "abs";
    }

    @Override
    BigRational applySingle(BigRational argValue) {
        return argValue.abs();
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.abs(argValue);
    }
}

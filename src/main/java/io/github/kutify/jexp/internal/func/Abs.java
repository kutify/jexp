package io.github.kutify.jexp.internal.func;

import io.github.kutify.jexp.api.BigRational;

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

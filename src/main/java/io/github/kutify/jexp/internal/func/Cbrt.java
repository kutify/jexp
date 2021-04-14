package io.github.kutify.jexp.internal.func;

import io.github.kutify.jexp.api.BigRational;

import java.math.BigInteger;

public class Cbrt extends OneArgFunction {

    private static final BigRational ONE_THIRD = new BigRational(BigInteger.ONE, BigInteger.valueOf(3));

    @Override
    public String getName() {
        return "cbrt";
    }

    @Override
    BigRational applySingle(BigRational argValue) {
        return argValue.pow(ONE_THIRD);
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.cbrt(argValue);
    }
}

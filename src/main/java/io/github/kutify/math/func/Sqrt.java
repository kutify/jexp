package io.github.kutify.math.func;

import io.github.kutify.math.number.BigRational;

import java.math.BigInteger;

public class Sqrt extends OneArgFunction {

    private static final BigRational HALF = new BigRational(BigInteger.ONE, BigInteger.valueOf(2));

    @Override
    public String getName() {
        return "sqrt";
    }

    @Override
    BigRational applySingle(BigRational argValue) {
        return argValue.pow(HALF);
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.sqrt(argValue);
    }
}

package io.github.kutify.math.func;

import io.github.kutify.math.expression.Function;
import io.github.kutify.math.number.BigRational;

import java.math.BigInteger;
import java.util.List;

public class Sqrt implements Function {

    private static final BigRational HALF = new BigRational(BigInteger.ONE, BigInteger.valueOf(2));

    @Override
    public String getName() {
        return "sqrt";
    }

    @Override
    public int getArgsNumber() {
        return 1;
    }

    @Override
    public Double applyDouble(List<Double> argValues) {
        return Math.sqrt(argValues.get(0));
    }

    @Override
    public BigRational applyRational(List<BigRational> argValues) {
        return argValues.get(0).pow(HALF);
    }
}

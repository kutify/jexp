package io.github.kutify.jexp.internal.func;

import io.github.kutify.jexp.api.Function;
import io.github.kutify.jexp.api.BigRational;

import java.util.List;

public class Mult implements Function {

    @Override
    public String getName() {
        return "mult";
    }

    @Override
    public Double applyDouble(List<Double> argValues) {
        double result = 1;
        for (double arg : argValues) {
            result *= arg;
        }
        return result;
    }

    @Override
    public BigRational applyRational(List<BigRational> argValues) {
        BigRational result = BigRational.ONE;
        for (BigRational arg : argValues) {
            result = result.multiply(arg);
        }
        return result;
    }
}

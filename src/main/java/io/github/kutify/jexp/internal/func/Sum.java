package io.github.kutify.jexp.internal.func;

import io.github.kutify.jexp.api.Function;
import io.github.kutify.jexp.api.BigRational;

import java.util.List;

public class Sum implements Function {

    @Override
    public String getName() {
        return "sum";
    }

    @Override
    public Double applyDouble(List<Double> argValues) {
        double result = 0;
        for (double arg : argValues) {
            result += arg;
        }
        return result;
    }

    @Override
    public BigRational applyRational(List<BigRational> argValues) {
        BigRational result = BigRational.ZERO;
        for (BigRational arg : argValues) {
            result = result.add(arg);
        }
        return result;
    }
}

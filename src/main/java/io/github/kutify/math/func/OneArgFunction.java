package io.github.kutify.math.func;

import io.github.kutify.math.api.Function;
import io.github.kutify.math.number.BigRational;

import java.util.List;

abstract class OneArgFunction implements Function {

    @Override
    public int getArgsNumber() {
        return 1;
    }

    @Override
    public BigRational applyRational(List<BigRational> argValues) {
        return applySingle(argValues.get(0));
    }

    @Override
    public Double applyDouble(List<Double> argValues) {
        return applySingle(argValues.get(0));
    }

    abstract BigRational applySingle(BigRational argValue);

    abstract Double applySingle(Double argValue);
}

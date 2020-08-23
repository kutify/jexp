package io.github.kutify.math.func;

import io.github.kutify.math.expression.Function;
import io.github.kutify.math.number.BigRational;

import java.util.List;

public class Abs implements Function {

    @Override
    public String getName() {
        return "abs";
    }

    @Override
    public int getArgsNumber() {
        return 1;
    }

    @Override
    public BigRational apply(List<BigRational> argValues) {
        return argValues.get(0).abs();
    }
}

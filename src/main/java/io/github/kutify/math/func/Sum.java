package io.github.kutify.math.func;

import io.github.kutify.math.expression.Function;
import io.github.kutify.math.number.BigRational;

import java.util.List;

public class Sum implements Function {

    @Override
    public String getName() {
        return "sum";
    }

    @Override
    public BigRational apply(List<BigRational> argValues) {
        BigRational result = BigRational.ZERO;
        for (BigRational arg : argValues) {
            result = result.add(arg);
        }
        return result;
    }
}

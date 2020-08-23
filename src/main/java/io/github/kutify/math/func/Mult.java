package io.github.kutify.math.func;

import io.github.kutify.math.expression.Function;
import io.github.kutify.math.number.BigRational;

import java.util.List;

public class Mult implements Function {

    @Override
    public String getName() {
        return "mult";
    }

    @Override
    public BigRational apply(List<BigRational> argValues) {
        BigRational result = BigRational.ONE;
        for (BigRational arg : argValues) {
            result = result.multiply(arg);
        }
        return result;
    }
}

package io.github.kutify.math.func;

import io.github.kutify.math.number.BigRational;

public class Signum extends OneArgFunction {

    @Override
    public String getName() {
        return "signum";
    }

    @Override
    BigRational applySingle(BigRational argValue) {
        return BigRational.valueOf(argValue.signum());
    }

    @Override
    Double applySingle(Double argValue) {
        return argValue > 0 ? 1d : (argValue == 0 ? 0d : -1d);
    }
}

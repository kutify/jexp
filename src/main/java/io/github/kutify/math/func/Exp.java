package io.github.kutify.math.func;

public class Exp extends OneDoubleArgFunction {

    @Override
    public String getName() {
        return "exp";
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.exp(argValue);
    }
}

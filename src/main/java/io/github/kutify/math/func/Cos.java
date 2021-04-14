package io.github.kutify.math.func;

public class Cos extends OneDoubleArgFunction {

    @Override
    public String getName() {
        return "cos";
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.cos(argValue);
    }
}

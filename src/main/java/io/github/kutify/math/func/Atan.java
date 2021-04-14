package io.github.kutify.math.func;

public class Atan extends OneDoubleArgFunction {

    @Override
    public String getName() {
        return "atan";
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.atan(argValue);
    }
}

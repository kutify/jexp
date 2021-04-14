package io.github.kutify.math.func;

public class Acos extends OneDoubleArgFunction {

    @Override
    public String getName() {
        return "acos";
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.acos(argValue);
    }
}

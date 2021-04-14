package io.github.kutify.jexp.internal.func;

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

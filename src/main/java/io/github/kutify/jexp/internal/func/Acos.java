package io.github.kutify.jexp.internal.func;

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

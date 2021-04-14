package io.github.kutify.jexp.internal.func;

public class Tan extends OneDoubleArgFunction {

    @Override
    public String getName() {
        return "tan";
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.tan(argValue);
    }
}

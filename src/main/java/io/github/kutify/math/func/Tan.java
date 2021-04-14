package io.github.kutify.math.func;

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

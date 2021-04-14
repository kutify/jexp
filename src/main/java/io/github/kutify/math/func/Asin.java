package io.github.kutify.math.func;

public class Asin extends OneDoubleArgFunction {

    @Override
    public String getName() {
        return "asin";
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.asin(argValue);
    }
}

package io.github.kutify.jexp.internal.func;

public class Sin extends OneDoubleArgFunction {

    @Override
    public String getName() {
        return "sin";
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.sin(argValue);
    }
}

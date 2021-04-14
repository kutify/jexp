package io.github.kutify.jexp.internal.func;

public class Log10 extends OneDoubleArgFunction {

    @Override
    public String getName() {
        return "log10";
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.log10(argValue);
    }
}

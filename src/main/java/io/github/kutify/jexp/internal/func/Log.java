package io.github.kutify.jexp.internal.func;

public class Log extends OneDoubleArgFunction {

    @Override
    public String getName() {
        return "log";
    }

    @Override
    Double applySingle(Double argValue) {
        return Math.log(argValue);
    }
}

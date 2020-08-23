package io.github.kutify.math.expression;

import java.util.HashMap;
import java.util.Map;

public class ExpressionContext {

    private final Map<String, Function> functions;

    public ExpressionContext() {
        functions = new HashMap<>();
    }

    public ExpressionContext(ExpressionContext context) {
        functions = new HashMap<>(context.functions);
    }

    public void registerFunction(Function function) {
        String name = function.getName();
        if (name == null) {
            throw new NullPointerException();
        }
        if (functions.containsKey(name)) {
            throw new RuntimeException(); // TODO
        }
        functions.put(function.getName(), function);
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }
}

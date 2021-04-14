package io.github.kutify.math.expression;

import io.github.kutify.math.api.Arguments;
import io.github.kutify.math.api.Expression;

import java.util.Map;
import java.util.function.Function;

public class ExpressionImpl<T> implements Expression<T> {

    private final Function<Map<String, T>, T> evalFunction;

    ExpressionImpl(Function<Map<String, T>, T> evalFunction) {
        this.evalFunction = evalFunction;
    }

    @Override
    public T evaluate(Arguments<T> arguments) {
        return evalFunction.apply(arguments.getArguments());
    }
}

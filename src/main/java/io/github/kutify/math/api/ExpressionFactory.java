package io.github.kutify.math.api;

import io.github.kutify.math.number.BigRational;

public interface ExpressionFactory {

    void register(Function function);

    void register(Constant constant);

    default Expression<Double> compile(String expression) {
        return compileDouble(expression);
    }

    Expression<Double> compileDouble(String expression);

    Expression<BigRational> compileRational(String expression);
}

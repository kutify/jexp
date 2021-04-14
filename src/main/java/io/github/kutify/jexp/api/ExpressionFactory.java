package io.github.kutify.jexp.api;

public interface ExpressionFactory {

    void register(Function function);

    void register(Constant constant);

    default Expression<Double> compile(String expression) {
        return compileDouble(expression);
    }

    Expression<Double> compileDouble(String expression);

    Expression<BigRational> compileRational(String expression);
}

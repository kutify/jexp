package io.github.kutify.math.expression;

import io.github.kutify.math.expression.token.TokenHandler;
import io.github.kutify.math.expression.token.FunctionTokensWrapper;

import java.util.List;

public class DoubleCompiler extends AbstractCompiler<Double> {

    public DoubleCompiler(TokenHandler<FunctionTokensWrapper> functionWrapperTokenHandler) {
        super(functionWrapperTokenHandler);
    }

    @Override
    protected Double plus(Double left, Double right) {
        return left + right;
    }

    @Override
    protected Double minus(Double left, Double right) {
        return left - right;
    }

    @Override
    protected Double multiply(Double left, Double right) {
        return left * right;
    }

    @Override
    protected Double divide(Double left, Double right) {
        return left / right;
    }

    @Override
    protected Double pow(Double left, Double right) {
        return Math.pow(left, right);
    }

    @Override
    protected Double applyFunction(Function function, List<Double> args) {
        return function.applyDouble(args);
    }

}

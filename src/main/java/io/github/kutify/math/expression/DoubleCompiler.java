package io.github.kutify.math.expression;

import io.github.kutify.math.expression.token.OperandTokenHandler;
import io.github.kutify.math.expression.token.TokenHandler;
import io.github.kutify.math.expression.token.FunctionTokensWrapper;

import java.util.List;

public class DoubleCompiler extends AbstractCompiler<Double> {

    private final OperandTokenHandler<Double> operandTokenHandler = new OperandTokenHandler<Double>() {
        @Override
        protected Double parseValue(String value) {
            return Double.valueOf(value);
        }
    };

    public DoubleCompiler(TokenHandler<FunctionTokensWrapper> functionWrapperTokenHandler) {
        super(functionWrapperTokenHandler);
    }

    @Override
    protected OperandTokenHandler getOperandTokenHandler() {
        return operandTokenHandler;
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
    protected Double mod(Double left, Double right) {
        return left % right;
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

package io.github.kutify.math.expression;

import io.github.kutify.math.api.Constant;
import io.github.kutify.math.api.Function;
import io.github.kutify.math.expression.token.FunctionTokensWrapper;
import io.github.kutify.math.expression.token.OperandTokenHandler;
import io.github.kutify.math.expression.token.TokenHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class DoubleCompiler extends AbstractCompiler<Double> {

    private final OperandTokenHandler<Double> operandTokenHandler = new OperandTokenHandler<Double>(constantsProvider) {
        @Override
        protected Double parseValue(String value) {
            return Double.valueOf(value);
        }

        @Override
        protected Double getConstantValue(Constant constant) {
            return constant.getDoubleValue();
        }
    };

    public DoubleCompiler(TokenHandler<FunctionTokensWrapper> functionWrapperTokenHandler,
                          Supplier<Map<String, Constant>> constantsProvider) {
        super(functionWrapperTokenHandler, constantsProvider);
    }

    @Override
    protected OperandTokenHandler<Double> getOperandTokenHandler() {
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
        if (left < 0 && Math.floor(right) != right) {
            throw new ArithmeticException("Forbidden operation: negative " + left + " to the power of non integer " +
                right);
        }
        return Math.pow(left, right);
    }

    @Override
    protected Double applyFunction(Function function, List<Double> args) {
        return function.applyDouble(args);
    }

}

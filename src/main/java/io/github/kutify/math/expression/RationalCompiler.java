package io.github.kutify.math.expression;

import io.github.kutify.math.api.Constant;
import io.github.kutify.math.api.Function;
import io.github.kutify.math.expression.token.OperandTokenHandler;
import io.github.kutify.math.expression.token.TokenHandler;
import io.github.kutify.math.expression.token.FunctionTokensWrapper;
import io.github.kutify.math.number.BigRational;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RationalCompiler extends AbstractCompiler<BigRational> {

    private final OperandTokenHandler<BigRational> operandTokenHandler = new OperandTokenHandler<BigRational>(constantsProvider) {
        @Override
        protected BigRational parseValue(String value) {
            return BigRational.parse(value);
        }

        @Override
        protected BigRational getConstantValue(Constant constant) {
            return constant.getRationalValue();
        }
    };

    public RationalCompiler(TokenHandler<FunctionTokensWrapper> functionWrapperTokenHandler,
                            Supplier<Map<String, Constant>> constantsProvider) {
        super(functionWrapperTokenHandler, constantsProvider);
    }

    @Override
    protected OperandTokenHandler<BigRational> getOperandTokenHandler() {
        return operandTokenHandler;
    }

    @Override
    protected BigRational plus(BigRational left, BigRational right) {
        return left.add(right);
    }

    @Override
    protected BigRational minus(BigRational left, BigRational right) {
        return left.subtract(right);
    }

    @Override
    protected BigRational multiply(BigRational left, BigRational right) {
        return left.multiply(right);
    }

    @Override
    protected BigRational divide(BigRational left, BigRational right) {
        return left.divide(right);
    }

    @Override
    protected BigRational mod(BigRational left, BigRational right) {
        return left.mod(right);
    }

    @Override
    protected BigRational pow(BigRational left, BigRational right) {
        return left.pow(right);
    }

    @Override
    protected BigRational applyFunction(Function function, List<BigRational> args) {
        return function.applyRational(args);
    }
}

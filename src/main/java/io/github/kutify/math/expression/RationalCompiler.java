package io.github.kutify.math.expression;

import io.github.kutify.math.expression.token.TokenHandler;
import io.github.kutify.math.expression.token.FunctionTokensWrapper;
import io.github.kutify.math.number.BigRational;

import java.util.List;

public class RationalCompiler extends AbstractCompiler<BigRational> {

    public RationalCompiler(TokenHandler<FunctionTokensWrapper> functionWrapperTokenHandler) {
        super(functionWrapperTokenHandler);
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
    protected BigRational pow(BigRational left, BigRational right) {
        return left.pow(right);
    }

    @Override
    protected BigRational applyFunction(Function function, List<BigRational> args) {
        return function.applyRational(args);
    }
}

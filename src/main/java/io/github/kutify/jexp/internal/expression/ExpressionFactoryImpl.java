package io.github.kutify.jexp.internal.expression;

import io.github.kutify.jexp.api.Constant;
import io.github.kutify.jexp.api.Expression;
import io.github.kutify.jexp.api.ExpressionFactory;
import io.github.kutify.jexp.api.Function;
import io.github.kutify.jexp.exception.ExpressionSyntaxErrorType;
import io.github.kutify.jexp.exception.ExpressionSyntaxException;
import io.github.kutify.jexp.internal.expression.operand.FunctionOperand;
import io.github.kutify.jexp.internal.expression.operand.IOperand;
import io.github.kutify.jexp.internal.expression.token.FunctionTokensWrapper;
import io.github.kutify.jexp.internal.expression.token.Token;
import io.github.kutify.jexp.internal.expression.token.TokenHandler;
import io.github.kutify.jexp.api.BigRational;

import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpressionFactoryImpl implements ExpressionFactory {

    private final Map<String, Function> functions;
    private final Map<String, Constant> constants;
    private final DoubleCompiler doubleCompiler;
    private final RationalCompiler rationalCompiler;

    private ExpressionFactoryImpl(ExpressionFactoryImpl prototype) {
        functions = new HashMap<>(prototype.functions);
        constants = new HashMap<>(prototype.constants);
        doubleCompiler = new DoubleCompiler(new DoubleFunctionWrapperTokenHandler(), () -> constants);
        rationalCompiler = new RationalCompiler(new RationalFunctionWrapperTokenHandler(), () -> constants);
    }

    public ExpressionFactoryImpl() {
        functions = new HashMap<>();
        constants = new HashMap<>();
        doubleCompiler = new DoubleCompiler(new DoubleFunctionWrapperTokenHandler(), () -> constants);
        rationalCompiler = new RationalCompiler(new RationalFunctionWrapperTokenHandler(), () -> constants);
    }

    @Override
    public void register(Function function) {
        functions.put(
            Objects.requireNonNull(function.getName()),
            function
        );
    }

    @Override
    public void register(Constant constant) {
        constants.put(
            Objects.requireNonNull(constant.getName()),
            constant
        );
    }

    @Override
    public Expression<Double> compileDouble(String expression) {
        return doubleCompiler.compile(expression);
    }

    @Override
    public Expression<BigRational> compileRational(String expression) {
        return rationalCompiler.compile(expression);
    }

    public ExpressionFactoryImpl clone() {
        return new ExpressionFactoryImpl(this);
    }


    private class DoubleFunctionWrapperTokenHandler extends AbstractFunctionWrapperTokenHandler<Double> {

        @Override
        protected AbstractCompiler<Double> getCompiler() {
            return doubleCompiler;
        }
    }

    private class RationalFunctionWrapperTokenHandler extends AbstractFunctionWrapperTokenHandler<BigRational> {

        @Override
        protected AbstractCompiler<BigRational> getCompiler() {
            return rationalCompiler;
        }
    }

    private abstract class AbstractFunctionWrapperTokenHandler<T> implements TokenHandler<FunctionTokensWrapper> {

        @Override
        public void handle(FunctionTokensWrapper token, Deque<IOperand> operandStack) {
            Function function = functions.get(token.getFunctionName());
            if (function == null) {
                throw new ExpressionSyntaxException(ExpressionSyntaxErrorType.UNDEFINED_FUNCTION,
                    token.getPosition());
            }
            List<List<Token>> argsTokens = token.getArgs();
            if (function.getArgsNumber() > -1 && function.getArgsNumber() != argsTokens.size()) {
                throw new ExpressionSyntaxException(ExpressionSyntaxErrorType.WRONG_FUNCTION_ARGS_NUMBER,
                    token.getPosition());
            }
            List<IOperand> operands = argsTokens.stream()
                .map(getCompiler()::postfixTokensToOperand)
                .collect(Collectors.toList());
            operandStack.push(new FunctionOperand(function, operands));
        }

        protected abstract AbstractCompiler<T> getCompiler();
    }
}

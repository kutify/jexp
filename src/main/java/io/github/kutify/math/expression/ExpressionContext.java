package io.github.kutify.math.expression;

import io.github.kutify.math.exception.ExpressionSyntaxErrorType;
import io.github.kutify.math.exception.ExpressionSyntaxException;
import io.github.kutify.math.expression.operand.FunctionOperand;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.token.TokenHandler;
import io.github.kutify.math.expression.token.FunctionTokensWrapper;
import io.github.kutify.math.expression.token.Token;
import io.github.kutify.math.number.BigRational;

import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpressionContext {

    private final Map<String, Function> functions;
    private final DoubleCompiler doubleCompiler;
    private final RationalCompiler rationalCompiler;

    ExpressionContext() {
        this(null);
    }

    ExpressionContext(ExpressionContext context) {
        if (context == null) {
            functions = new HashMap<>();
        } else {
            functions = new HashMap<>(context.functions);
        }
        doubleCompiler = new DoubleCompiler(new AbstractFunctionWrapperTokenHandler() {
            @Override
            protected AbstractCompiler getCompiler() {
                return doubleCompiler;
            }
        });
        rationalCompiler = new RationalCompiler(new AbstractFunctionWrapperTokenHandler() {
            @Override
            protected AbstractCompiler getCompiler() {
                return rationalCompiler;
            }
        });
    }

    public void registerFunction(Function function) {
        String name = function.getName();
        Objects.requireNonNull(name);
        functions.put(name, function);
    }

    public Expression<BigRational> compileRational(String expression) {
        return rationalCompiler.compile(expression);
    }

    public Expression<Double> compileDouble(String expression) {
        return doubleCompiler.compile(expression);
    }

    public abstract class AbstractFunctionWrapperTokenHandler implements TokenHandler<FunctionTokensWrapper> {

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

        protected abstract AbstractCompiler getCompiler();
    }
}

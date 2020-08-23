package io.github.kutify.math.expression.parser;

import io.github.kutify.math.expression.Expression;
import io.github.kutify.math.expression.ExpressionContext;
import io.github.kutify.math.expression.Function;
import io.github.kutify.math.expression.operand.FunctionOperand;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.parser.token.FunctionTokensWrapper;
import io.github.kutify.math.expression.parser.token.Token;

import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionWrapperTokenHandler implements TokenHandler<FunctionTokensWrapper> {

    private final ExpressionContext context;

    public FunctionWrapperTokenHandler(ExpressionContext context) {
        this.context = context;
    }

    @Override
    public void handle(FunctionTokensWrapper token, Deque<IOperand> operandStack) {
        Function function = context.getFunction(token.getFunctionName());

        if (function == null) {
            throw new RuntimeException("Function is not defined");
        }

        List<List<Token>> argsTokens = token.getArgs();

        if (function.getArgsNumber() > -1 && function.getArgsNumber() != argsTokens.size()) {
            throw new RuntimeException("Wrong arguments number");
        }

        List<IOperand> operands = argsTokens.stream()
                .map(tokens -> Expression.postfixTokensToOperand(context, tokens))
                .collect(Collectors.toList());

        operandStack.push(new FunctionOperand(function, operands));
    }
}

package io.github.kutify.math.expression;

import io.github.kutify.math.exception.ExpressionSyntaxException;
import io.github.kutify.math.expression.operand.FunctionOperand;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.parser.OperandTokenHandler;
import io.github.kutify.math.expression.parser.OperatorTokenHandler;
import io.github.kutify.math.expression.parser.Parser;
import io.github.kutify.math.expression.parser.TokenHandler;
import io.github.kutify.math.expression.parser.token.FunctionTokensWrapper;
import io.github.kutify.math.expression.parser.token.OperandToken;
import io.github.kutify.math.expression.parser.token.OperatorToken;
import io.github.kutify.math.expression.parser.token.Token;
import io.github.kutify.math.expression.parser.token.TokenType;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpressionContext {

    private static final OperandTokenHandler OPERAND_TOKEN_HANDLER = new OperandTokenHandler();
    private static final OperatorTokenHandler OPERATOR_TOKEN_HANDLER = new OperatorTokenHandler();

    private final Map<String, Function> functions;
    private final TokenHandler<FunctionTokensWrapper> functionWrapperTokenHandler;

    ExpressionContext() {
        this(null);
    }

    ExpressionContext(ExpressionContext context) {
        if (context == null) {
            functions = new HashMap<>();
        } else {
            functions = new HashMap<>(context.functions);
        }
        functionWrapperTokenHandler = new FunctionWrapperTokenHandler();
    }

    public void registerFunction(Function function) {
        String name = function.getName();
        Objects.requireNonNull(name);
        functions.put(name, function);
    }

    public Expression parse(String expression) {
        try {
            List<Token> tokens = Parser.infixToPostfix(Parser.parseTokens(expression));
            return new Expression(postfixTokensToOperand(tokens));
        } catch (ExpressionSyntaxException ex) {
            if (ex.getExpression() == null) {
                throw new ExpressionSyntaxException(expression, ex.getErrorItems());
            } else {
                throw ex;
            }
        }
    }

    private IOperand postfixTokensToOperand(List<Token> tokens) {
        Deque<IOperand> operandStack = new LinkedList<>();

        for (Token token : tokens) {
            if (token.getType() == TokenType.OPERAND) {
                OPERAND_TOKEN_HANDLER.handle((OperandToken) token, operandStack);

            } else if (token.getType() == TokenType.OPERATOR) {
                OPERATOR_TOKEN_HANDLER.handle((OperatorToken) token, operandStack);

            } else if (token.getType() == TokenType.FUNCTION_TOKEN_WRAPPER) {
                functionWrapperTokenHandler.handle((FunctionTokensWrapper) token, operandStack);

            } else {
                throw new RuntimeException();
            }
        }

        return operandStack.pop();
    }


    private class FunctionWrapperTokenHandler implements TokenHandler<FunctionTokensWrapper> {

        @Override
        public void handle(FunctionTokensWrapper token, Deque<IOperand> operandStack) {
            Function function = functions.get(token.getFunctionName());

            if (function == null) {
                throw new RuntimeException("Function is not defined");
            }

            List<List<Token>> argsTokens = token.getArgs();

            if (function.getArgsNumber() > -1 && function.getArgsNumber() != argsTokens.size()) {
                throw new RuntimeException("Wrong arguments number");
            }

            List<IOperand> operands = argsTokens.stream()
                    .map(ExpressionContext.this::postfixTokensToOperand)
                    .collect(Collectors.toList());

            operandStack.push(new FunctionOperand(function, operands));
        }
    }
}

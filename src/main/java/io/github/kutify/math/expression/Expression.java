package io.github.kutify.math.expression;

import io.github.kutify.math.exception.ExpressionSyntaxException;
import io.github.kutify.math.expression.operand.FunctionOperand;
import io.github.kutify.math.expression.operand.IBinaryOperand;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.operand.ValueOperand;
import io.github.kutify.math.expression.operand.VarOperand;
import io.github.kutify.math.expression.operation.Operation;
import io.github.kutify.math.expression.parser.FunctionWrapperTokenHandler;
import io.github.kutify.math.expression.parser.OperandTokenHandler;
import io.github.kutify.math.expression.parser.OperatorTokenHandler;
import io.github.kutify.math.expression.parser.Parser;
import io.github.kutify.math.expression.parser.token.FunctionTokensWrapper;
import io.github.kutify.math.expression.parser.token.OperandToken;
import io.github.kutify.math.expression.parser.token.OperatorToken;
import io.github.kutify.math.expression.parser.token.Token;
import io.github.kutify.math.expression.parser.token.TokenType;
import io.github.kutify.math.func.Abs;
import io.github.kutify.math.func.Max;
import io.github.kutify.math.func.Min;
import io.github.kutify.math.func.Mult;
import io.github.kutify.math.func.Sqrt;
import io.github.kutify.math.func.Sum;
import io.github.kutify.math.number.BigRational;
import lombok.var;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Expression {

    private static final ExpressionContext DEFAULT_CONTEXT = new ExpressionContext();
    private static final ExpressionContext EMPTY_CONTEXT = new ExpressionContext();
    private static final OperandTokenHandler OPERAND_TOKEN_HANDLER = new OperandTokenHandler();
    private static final OperatorTokenHandler OPERATOR_TOKEN_HANDLER = new OperatorTokenHandler();

    static {
        Arrays.asList(
                new Abs(),
                new Max(),
                new Min(),
                new Mult(),
                new Sqrt(),
                new Sum()
        ).forEach(DEFAULT_CONTEXT::registerFunction);
    }

    private final IOperand rootOperand;
    private final Function<Map<String, BigRational>, BigRational> calcFunction;

    public static Expression compile(String expression) {
        return new Expression(parse(DEFAULT_CONTEXT, expression));
    }

    public static ExpressionContext newContext() {
        return new ExpressionContext(DEFAULT_CONTEXT);
    }

    public static ExpressionContext newEmptyContext() {
        return new ExpressionContext(EMPTY_CONTEXT);
    }

    Expression(IOperand rootOperand) {
        this.rootOperand = rootOperand;
        this.calcFunction = generateCalcFunction(rootOperand);
    }

    public BigRational calculate(Arguments arguments) {
        return calcFunction.apply(arguments.getArguments());
    }

    private Function<Map<String, BigRational>, BigRational> generateCalcFunction(IOperand operand) {
        Operation operation = operand.getOperation();

        if (operation == Operation.VALUE) {
            return varValues -> ((ValueOperand) operand).getValue();

        } else if (operation == Operation.VAR) {
            return varValues -> varValues.get(((VarOperand) operand).getVarName());

        } else if (operand instanceof IBinaryOperand) {
            IBinaryOperand binaryOperand = (IBinaryOperand) operand;
            Function<Map<String, BigRational>, BigRational> leftFunc = generateCalcFunction(binaryOperand.getLeft());
            Function<Map<String, BigRational>, BigRational> rightFunc = generateCalcFunction(binaryOperand.getRight());
            if (operation == Operation.PLUS) {
                return varValues -> leftFunc.apply(varValues).add(rightFunc.apply(varValues));
            } else if (operation == Operation.MINUS) {
                return varValues -> leftFunc.apply(varValues).subtract(rightFunc.apply(varValues));
            } else if (operation == Operation.MULTIPLY) {
                return varValues -> leftFunc.apply(varValues).multiply(rightFunc.apply(varValues));
            } else if (operation == Operation.DIVIDE) {
                return varValues -> leftFunc.apply(varValues).divide(rightFunc.apply(varValues));
            } else if (operation == Operation.POWER) {
                return varValues -> leftFunc.apply(varValues).pow(rightFunc.apply(varValues));
            } else {
                throw new RuntimeException();
            }

        } else if (operand instanceof FunctionOperand) {
            FunctionOperand functionOperand = (FunctionOperand) operand;
            var function = functionOperand.getFunction();
            List<Function<Map<String, BigRational>, BigRational>> funcs = functionOperand.getArguments().stream()
                    .map(this::generateCalcFunction)
                    .collect(Collectors.toList());
            return varValues -> function.apply(
                    funcs.stream()
                            .map(func -> func.apply(varValues))
                            .collect(Collectors.toList())
            );
        } else {
            throw new RuntimeException();
        }
    }

    private static IOperand parse(ExpressionContext context, String expression) {
        try {
            List<Token> tokens = Parser.infixToPostfix(Parser.parseTokens(expression));
            return postfixTokensToOperand(context, tokens);
        } catch (ExpressionSyntaxException ex) {
            if (ex.getExpression() == null) {
                throw new ExpressionSyntaxException(expression, ex.getErrorItems());
            } else {
                throw ex;
            }
        }
    }

    public static IOperand postfixTokensToOperand(ExpressionContext context, List<Token> tokens) {
        Deque<IOperand> operandStack = new LinkedList<>();
        final FunctionWrapperTokenHandler functionWrapperTokenHandler = new FunctionWrapperTokenHandler(context);

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
}

package io.github.kutify.math.expression;

import io.github.kutify.math.exception.ExpressionSyntaxException;
import io.github.kutify.math.expression.operand.IBinaryOperand;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.operand.ValueOperand;
import io.github.kutify.math.expression.operand.VarOperand;
import io.github.kutify.math.expression.operation.Operation;
import io.github.kutify.math.expression.parser.OperandTokenHandler;
import io.github.kutify.math.expression.parser.OperatorTokenHandler;
import io.github.kutify.math.expression.parser.Parser;
import io.github.kutify.math.expression.parser.token.OperandToken;
import io.github.kutify.math.expression.parser.token.OperatorToken;
import io.github.kutify.math.expression.parser.token.Token;
import io.github.kutify.math.expression.parser.token.TokenType;
import io.github.kutify.math.number.BigRational;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Expression {

    private static final OperandTokenHandler OPERAND_TOKEN_HANDLER = new OperandTokenHandler();
    private static final OperatorTokenHandler OPERATOR_TOKEN_HANDLER = new OperatorTokenHandler();

    private final IOperand rootOperand;
    private final Function<Map<String, BigRational>, BigRational> calcFunction;

    public static Expression compile(String expression) {
        return new Expression(parse(expression));
    }

    private Expression(IOperand rootOperand) {
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
                return varValues -> leftFunc.apply(varValues).pow(rightFunc.apply(varValues).intValue());
            } else {
                throw new RuntimeException();
            }

        } else {
            throw new RuntimeException();
        }
    }

    private static IOperand parse(String expression) {
        try {
            List<Token> tokens = Parser.parse(expression);

            tokens = Parser.infixToPostfix(tokens);

            Deque<IOperand> operandStack = new LinkedList<>();

            for (Token token : tokens) {
                if (token.getType() == TokenType.OPERAND) {
                    OPERAND_TOKEN_HANDLER.handle((OperandToken) token, operandStack);

                } else if (token.getType() == TokenType.OPERATOR) {
                    OPERATOR_TOKEN_HANDLER.handle((OperatorToken) token, operandStack);

                } else {
                    throw new RuntimeException();
                }
            }

            return operandStack.pop();

        } catch (ExpressionSyntaxException ex) {
            if (ex.getExpression() == null) {
                throw new ExpressionSyntaxException(expression, ex.getErrorItems());
            } else {
                throw ex;
            }
        }
    }
}

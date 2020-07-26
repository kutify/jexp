package io.github.kutify.math.expression;

import io.github.kutify.math.exception.ExpressionSyntaxException;
import io.github.kutify.math.exception.VariableFormatException;
import io.github.kutify.math.expression.operand.DivideOperand;
import io.github.kutify.math.expression.operand.IBinaryOperand;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.operand.MinusOperand;
import io.github.kutify.math.expression.operand.MultiplyOperand;
import io.github.kutify.math.expression.operand.PlusOperand;
import io.github.kutify.math.expression.operand.PowerOperand;
import io.github.kutify.math.expression.operand.ValueOperand;
import io.github.kutify.math.expression.operand.VarOperand;
import io.github.kutify.math.expression.operation.Operation;
import io.github.kutify.math.expression.parser.Parser;
import io.github.kutify.math.expression.parser.token.OperandToken;
import io.github.kutify.math.expression.parser.token.OperatorToken;
import io.github.kutify.math.expression.parser.token.OperatorType;
import io.github.kutify.math.expression.parser.token.Token;
import io.github.kutify.math.expression.parser.token.TokenType;
import io.github.kutify.math.number.BigRational;
import io.github.kutify.math.exception.ExpressionSyntaxErrorType;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Expression {

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
        Deque<IOperand> operandStack = new LinkedList<>();

        List<Token> tokens = Parser.parse(expression);
        try {
            tokens = Parser.infixToPostfix(tokens);
        } catch (ExpressionSyntaxException ex) {
            throw new ExpressionSyntaxException(expression, ex.getErrorItems());
        }

        for (Token token : tokens) {
            if (token.getType() == TokenType.OPERAND) {
                OperandToken operandToken = (OperandToken) token;
                String value = operandToken.getValue();
                IOperand operand;
                if (Character.isDigit(value.charAt(0))) { // TODO Change it to proper validation
                    try {
                        operand = new ValueOperand(BigRational.parse(value));
                    } catch (NumberFormatException ex) {
                        throw new ExpressionSyntaxException(expression, ExpressionSyntaxErrorType.INVALID_NUMBER_FORMAT,
                                operandToken.getPosition());
                    }
                } else {
                    try {
                        operand = new VarOperand(value);
                    } catch (VariableFormatException ex) {
                        throw new ExpressionSyntaxException(expression, ExpressionSyntaxErrorType.INVALID_VARIABLE_FORMAT,
                                operandToken.getPosition() + ex.getPosition());
                    }
                }
                operandStack.push(operand);
                continue;
            }
            if (token.getType() == TokenType.OPERATOR) {
                OperatorToken operatorToken = (OperatorToken) token;
                OperatorType operatorType = operatorToken.getOperatorType();
                IOperand right = operandStack.pop();
                IOperand left = operandStack.pop();
                if (operatorType == OperatorType.PLUS) {
                    operandStack.push(new PlusOperand(left, right));
                } else if (operatorType == OperatorType.MINUS) {
                    operandStack.push(new MinusOperand(left, right));
                } else if (operatorType == OperatorType.MULTIPLY) {
                    operandStack.push(new MultiplyOperand(left, right));
                } else if (operatorType == OperatorType.DIVIDE) {
                    operandStack.push(new DivideOperand(left, right));
                } else if (operatorType == OperatorType.POWER) {
                    operandStack.push(new PowerOperand(left, right));
                }
                continue;
            }
            throw new RuntimeException();
        }

        return operandStack.pop();
    }
}

package io.github.kutify.math.expression;

import io.github.kutify.math.api.Constant;
import io.github.kutify.math.api.Function;
import io.github.kutify.math.exception.ExpressionSyntaxException;
import io.github.kutify.math.expression.token.OperandTokenHandler;
import io.github.kutify.math.expression.token.OperatorTokenHandler;
import io.github.kutify.math.expression.token.TokenHandler;
import io.github.kutify.math.expression.token.CommaToken;
import io.github.kutify.math.expression.token.FunctionTokensWrapper;
import io.github.kutify.math.expression.token.OperandToken;
import io.github.kutify.math.expression.token.OperatorToken;
import io.github.kutify.math.expression.token.OperatorType;
import io.github.kutify.math.expression.token.ParenthesisToken;
import io.github.kutify.math.expression.token.Token;
import io.github.kutify.math.expression.token.TokenType;
import io.github.kutify.math.exception.ExpressionSyntaxErrorType;
import io.github.kutify.math.expression.operand.FunctionOperand;
import io.github.kutify.math.expression.operand.IBinaryOperand;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.operand.ValueOperand;
import io.github.kutify.math.expression.operand.VarOperand;
import io.github.kutify.math.expression.operation.Operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractCompiler<T> {

    static final char OPENING_PARENTHESIS = '(';
    static final char CLOSING_PARENTHESIS = ')';

    static final char PLUS = '+';
    static final char MINUS = '-';
    static final char MULTIPLY = '*';
    static final char DIVIDE = '/';
    static final char POWER = '^';
    static final char MOD = '%';
    static final char COMMA = ',';

    static final char SPACE = ' ';
    static final char POINT = '.';
    static final char UNDERSCORE = '_';

    private static final OperatorTokenHandler OPERATOR_TOKEN_HANDLER = new OperatorTokenHandler();

    final TokenHandler<FunctionTokensWrapper> functionWrapperTokenHandler;
    protected final Supplier<Map<String, Constant>> constantsProvider;

    protected AbstractCompiler(TokenHandler<FunctionTokensWrapper> functionWrapperTokenHandler,
                               Supplier<Map<String, Constant>> constantsProvider) {
        this.functionWrapperTokenHandler = functionWrapperTokenHandler;
        this.constantsProvider = constantsProvider;
    }

    public ExpressionImpl<T> compile(String expression) {
        try {
            List<Token> tokens = infixToPostfix(parseTokens(expression));
            return new ExpressionImpl<>(
                generateEvalFunction(
                    postfixTokensToOperand(tokens)
                )
            );
        } catch (ExpressionSyntaxException ex) {
            if (ex.getExpression() == null) {
                throw new ExpressionSyntaxException(expression, ex.getErrorItems());
            } else {
                throw ex;
            }
        } catch (Exception ex) {
            throw new ExpressionSyntaxException(expression, ex);
        }
    }

    private static List<Token> parseTokens(String expression) {
        char[] chars = expression.toCharArray();
        List<Token> tokens = new ArrayList<>();
        int operandStart = 0;
        boolean prevWasOperandSymbol = false;
        for (int i = 0; i <= chars.length; i++) {
            char symbol = i == chars.length ? SPACE : chars[i];
            if (isOperandPart(symbol)) {
                if (!prevWasOperandSymbol) {
                    operandStart = i;
                }
                prevWasOperandSymbol = true;
            } else {
                if (prevWasOperandSymbol) {
                    tokens.add(new OperandToken(
                            operandStart,
                            new String(Arrays.copyOfRange(chars, operandStart, i))
                    ));
                }
                if (symbol != SPACE) {
                    Token token = generateTokenIfPossible(symbol, i);
                    if (token == null) {
                        throw new ExpressionSyntaxException(expression, ExpressionSyntaxErrorType.INVALID_SYMBOL, i);
                    }
                    tokens.add(token);
                }
                prevWasOperandSymbol = false;
            }
        }
        return tokens;
    }

    private static List<Token> infixToPostfix(List<Token> tokens) {
        tokens = makeInfixListStrict(tokens);
        List<Token> result = new LinkedList<>();
        Deque<Token> operators = new LinkedList<>();
        final int size = tokens.size();
        for (int i = 0; i < size; i++) {
            Token token = tokens.get(i);
            TokenType tokenType = token.getType();
            if (tokenType == TokenType.OPERAND) {
                Token nextToken = i == size - 1 ? null : tokens.get(i + 1);
                if (nextToken == null ||
                        !(TokenType.PARENTHESIS.equals(nextToken.getType()) &&
                                (((ParenthesisToken) nextToken).isOpening()))) {
                    result.add(token);
                } else {
                    List<List<Token>> subTokens = new ArrayList<>();
                    i = 1 + fillFunctionArgs(tokens, i + 1, subTokens);
                    result.add(new FunctionTokensWrapper(
                            token.getPosition(),
                            ((OperandToken) token).getValue(),
                            subTokens.stream()
                                    .<List<Token>>map(AbstractCompiler::infixToPostfix)
                                    .collect(Collectors.toList())
                    ));
                }
            } else if (tokenType == TokenType.OPERATOR) {
                OperatorToken operator = (OperatorToken) token;
                while (!operators.isEmpty()) {
                    Token topOperator = operators.peek();
                    if (topOperator.getType() == TokenType.PARENTHESIS) {
                        break;

                    } else if (topOperator.getType() == TokenType.OPERATOR) {
                        OperatorToken topOp = (OperatorToken) topOperator;
                        if (topOp.hasHigherOrEqualPrecedence(operator)) {
                            result.add(operators.pop());
                        } else {
                            break;
                        }
                    } else {
                        throw new RuntimeException();
                    }
                }
                operators.push(operator);
            } else if (tokenType == TokenType.PARENTHESIS) {
                ParenthesisToken parenthesis = (ParenthesisToken) token;
                if (parenthesis.isOpening()) {
                    operators.push(parenthesis);
                } else {
                    while (true) {
                        try {
                            Token topOperator = operators.pop();
                            if (topOperator.getType() == TokenType.PARENTHESIS) {
                                break;
                            } else {
                                result.add(topOperator);
                            }
                        } catch (NoSuchElementException ex) {
                            throw new ExpressionSyntaxException(null, ExpressionSyntaxErrorType.NO_OPENING_PARENTHESIS,
                                    token.getPosition());
                        }
                    }
                }
            } else {
                throw new RuntimeException();
            }
        }

        while (!operators.isEmpty()) {
            Token operator = operators.pop();
            if (operator.getType() == TokenType.PARENTHESIS) {
                throw new ExpressionSyntaxException(null, ExpressionSyntaxErrorType.NO_CLOSING_PARENTHESIS,
                        operator.getPosition());
            }
            result.add(operator);
        }
        return result;
    }

    public IOperand postfixTokensToOperand(List<Token> tokens) {
        Deque<IOperand> operandStack = new LinkedList<>();
        for (Token token : tokens) {
            if (token.getType() == TokenType.OPERAND) {
                getOperandTokenHandler().handle((OperandToken) token, operandStack);
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

    private java.util.function.Function<Map<String, T>, T> generateEvalFunction(IOperand operand) {
        Operation operation = operand.getOperation();

        if (operation == Operation.VALUE) {
            T value = ((ValueOperand<T>) operand).getValue();
            return varValues -> value;
        } else if (operation == Operation.VAR) {
            String varName = ((VarOperand) operand).getVarName();
            return varValues -> varValues.get(varName);
        } else if (operand instanceof IBinaryOperand) {
            IBinaryOperand binaryOperand = (IBinaryOperand) operand;
            java.util.function.Function<Map<String, T>, T> leftFunc = generateEvalFunction(binaryOperand.getLeft());
            java.util.function.Function<Map<String, T>, T> rightFunc = generateEvalFunction(binaryOperand.getRight());
            if (operation == Operation.PLUS) {
                return varValues -> plus(leftFunc.apply(varValues), rightFunc.apply(varValues));
            } else if (operation == Operation.MINUS) {
                return varValues -> minus(leftFunc.apply(varValues), rightFunc.apply(varValues));
            } else if (operation == Operation.MULTIPLY) {
                return varValues -> multiply(leftFunc.apply(varValues), rightFunc.apply(varValues));
            } else if (operation == Operation.DIVIDE) {
                return varValues -> divide(leftFunc.apply(varValues), rightFunc.apply(varValues));
            } else if (operation == Operation.MOD) {
                return varValues -> mod(leftFunc.apply(varValues), rightFunc.apply(varValues));
            } else if (operation == Operation.POWER) {
                return varValues -> pow(leftFunc.apply(varValues), (rightFunc.apply(varValues)));
            } else {
                throw new RuntimeException();
            }
        } else if (operand instanceof FunctionOperand) {
            FunctionOperand functionOperand = (FunctionOperand) operand;
            Function function = functionOperand.getFunction();
            List<java.util.function.Function<Map<String, T>, T>> funcs = functionOperand
                    .getArguments()
                    .stream()
                    .map(this::generateEvalFunction)
                    .collect(Collectors.toList());
            return varValues -> applyFunction(
                    function,
                    funcs.stream()
                            .map(func -> func.apply(varValues))
                            .collect(Collectors.toList())
            );
        } else {
            throw new RuntimeException();
        }
    }

    protected abstract OperandTokenHandler<T> getOperandTokenHandler();
    protected abstract T plus(T left, T right);
    protected abstract T minus(T left, T right);
    protected abstract T multiply(T left, T right);
    protected abstract T divide(T left, T right);
    protected abstract T mod(T left, T right);
    protected abstract T pow(T left, T right);
    protected abstract T applyFunction(Function function, List<T> args);

    // Returns closing parenthesis position
    private static int fillFunctionArgs(List<Token> input, int openingParenthesisPos, List<List<Token>> output) {
        final int size = input.size();
        List<Token> sublist = new ArrayList<>();
        int parenthesisCounter = 1;
        int i = openingParenthesisPos + 1;
        for (; i < size; i++) {
            Token token = input.get(i);
            TokenType tokenType = token.getType();
            if (TokenType.COMMA.equals(tokenType)) {
                output.add(sublist);
                sublist = new ArrayList<>();
            } else {
                if (TokenType.PARENTHESIS.equals(tokenType)) {
                    ParenthesisToken parenthesisToken = (ParenthesisToken) token;
                    parenthesisCounter += parenthesisToken.isOpening() ? 1 : -1;
                } else {
                    sublist.add(token);
                }
            }
            if (parenthesisCounter == 0) {
                output.add(sublist);
                break;
            }
        }
        return i;
    }

    private static List<Token> makeInfixListStrict(List<Token> tokens) {
        List<Token> result = new ArrayList<>(tokens.size() * 3 / 2);
        Token prevToken = null;
        for (Token token : tokens) {
            if (prevToken == null ||
                    (prevToken.getType() == TokenType.PARENTHESIS && ((ParenthesisToken) prevToken).isOpening())) {
                if (token.getType() == TokenType.OPERATOR) {
                    OperatorType operatorType = ((OperatorToken) token).getOperatorType();
                    if (operatorType == OperatorType.MINUS || operatorType == OperatorType.PLUS) {
                        result.add(new OperandToken(-1, "0"));
                    }
                }
            } else if (token.getType() == TokenType.OPERAND && prevToken.getType() == TokenType.OPERAND) {
                result.add(new OperatorToken(token.getPosition(), OperatorType.MULTIPLY));
            }
            result.add(token);
            prevToken = token;
        }
        return result;
    }

    private static Token generateTokenIfPossible(char symbol, int position) {
        if (symbol == OPENING_PARENTHESIS) {
            return new ParenthesisToken(position, true);
        }
        if (symbol == CLOSING_PARENTHESIS) {
            return new ParenthesisToken(position, false);
        }
        if (symbol == PLUS) {
            return new OperatorToken(position, OperatorType.PLUS);
        }
        if (symbol == MINUS) {
            return new OperatorToken(position, OperatorType.MINUS);
        }
        if (symbol == MULTIPLY) {
            return new OperatorToken(position, OperatorType.MULTIPLY);
        }
        if (symbol == DIVIDE) {
            return new OperatorToken(position, OperatorType.DIVIDE);
        }
        if (symbol == POWER) {
            return new OperatorToken(position, OperatorType.POWER);
        }
        if (symbol == MOD) {
            return new OperatorToken(position, OperatorType.MOD);
        }
        if (symbol == COMMA) {
            return new CommaToken(position);
        }
        return null;
    }

    private static boolean isOperandPart(char symbol) {
        return Character.isLetterOrDigit(symbol) || symbol == POINT || symbol == UNDERSCORE;
    }
}

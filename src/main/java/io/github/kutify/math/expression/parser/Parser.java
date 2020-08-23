package io.github.kutify.math.expression.parser;

import io.github.kutify.math.exception.ExpressionSyntaxException;
import io.github.kutify.math.expression.parser.token.CommaToken;
import io.github.kutify.math.expression.parser.token.FunctionTokensWrapper;
import io.github.kutify.math.expression.parser.token.OperandToken;
import io.github.kutify.math.expression.parser.token.OperatorToken;
import io.github.kutify.math.expression.parser.token.OperatorType;
import io.github.kutify.math.expression.parser.token.ParenthesisToken;
import io.github.kutify.math.expression.parser.token.Token;
import io.github.kutify.math.expression.parser.token.TokenType;
import io.github.kutify.math.exception.ExpressionSyntaxErrorType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public abstract class Parser {

    final static char OPENING_PARENTHESIS = '(';
    final static char CLOSING_PARENTHESIS = ')';

    final static char PLUS = '+';
    final static char MINUS = '-';
    final static char MULTIPLY = '*';
    final static char DIVIDE = '/';
    final static char POWER = '^';
    final static char COMMA = ',';

    static final char SPACE = ' ';
    static final char POINT = '.';
    static final char UNDERSCORE = '_';

    private Parser() {
    }

    public static List<Token> infixToPostfix(List<Token> tokens) {
        tokens = fixOperatorAtStart(tokens);

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
                            i,
                            ((OperandToken) token).getValue(),
                            subTokens.stream()
                                    .map(Parser::infixToPostfix)
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

    public static List<Token> parseTokens(String expression) {
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

    private static List<Token> fixOperatorAtStart(List<Token> tokens) {
        List<Token> result = new LinkedList<>();
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
        if (symbol == COMMA) {
            return new CommaToken(position);
        }
        return null;
    }

    private static boolean isOperandPart(char symbol) {
        return Character.isLetterOrDigit(symbol) || symbol == POINT || symbol == UNDERSCORE;
    }
}

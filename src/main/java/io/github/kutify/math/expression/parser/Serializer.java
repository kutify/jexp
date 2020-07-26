package io.github.kutify.math.expression.parser;

import io.github.kutify.math.expression.parser.token.OperandToken;
import io.github.kutify.math.expression.parser.token.OperatorToken;
import io.github.kutify.math.expression.parser.token.OperatorType;
import io.github.kutify.math.expression.parser.token.ParenthesisToken;
import io.github.kutify.math.expression.parser.token.Token;
import io.github.kutify.math.expression.parser.token.TokenType;

import java.util.List;

public abstract class Serializer {

    private Serializer() {
    }

    public static String serialize(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();

        for (Token token : tokens) {
            sb.append(serializeToken(token));
            sb.append(Parser.SPACE);
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    private static String serializeToken(Token token) {
        if (token.getType() == TokenType.PARENTHESIS) {
            ParenthesisToken parenthesis = (ParenthesisToken) token;
            if (parenthesis.isOpening()) {
                return String.valueOf(Parser.OPENING_PARENTHESIS);
            } else {
                return String.valueOf(Parser.CLOSING_PARENTHESIS);
            }
        }
        if (token.getType() == TokenType.OPERATOR) {
            OperatorToken operator = (OperatorToken) token;
            OperatorType type = operator.getOperatorType();
            if (type == OperatorType.PLUS) {
                return String.valueOf(Parser.PLUS);
            }
            if (type == OperatorType.MINUS) {
                return String.valueOf(Parser.MINUS);
            }
            if (type == OperatorType.MULTIPLY) {
                return String.valueOf(Parser.MULTIPLY);
            }
            if (type == OperatorType.DIVIDE) {
                return String.valueOf(Parser.DIVIDE);
            }
            if (type == OperatorType.POWER) {
                return String.valueOf(Parser.POWER);
            }
        }
        if (token.getType() == TokenType.OPERAND) {
            return ((OperandToken) token).getValue();
        }
        throw new IllegalArgumentException();
    }
}

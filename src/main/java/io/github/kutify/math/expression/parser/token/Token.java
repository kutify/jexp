package io.github.kutify.math.expression.parser.token;

public interface Token {

    TokenType getType();

    int getPosition();

    boolean equalsRegardlessPosition(Token o);
}

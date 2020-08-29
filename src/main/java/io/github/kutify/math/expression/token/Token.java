package io.github.kutify.math.expression.token;

public interface Token {

    TokenType getType();

    int getPosition();

    boolean equalsRegardlessPosition(Token o);
}

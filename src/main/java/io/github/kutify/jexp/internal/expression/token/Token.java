package io.github.kutify.jexp.internal.expression.token;

public interface Token {

    TokenType getType();

    int getPosition();

    boolean equalsRegardlessPosition(Token o);
}

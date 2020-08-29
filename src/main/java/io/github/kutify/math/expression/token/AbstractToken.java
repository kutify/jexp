package io.github.kutify.math.expression.token;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractToken implements Token {

    private final TokenType type;
    private final int position;

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public int getPosition() {
        return position;
    }
}

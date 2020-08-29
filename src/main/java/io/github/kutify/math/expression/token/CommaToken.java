package io.github.kutify.math.expression.token;

public class CommaToken extends AbstractToken {

    public CommaToken(int position) {
        super(TokenType.COMMA, position);
    }

    @Override
    public boolean equalsRegardlessPosition(Token o) {
        return o.getType() == TokenType.COMMA;
    }
}

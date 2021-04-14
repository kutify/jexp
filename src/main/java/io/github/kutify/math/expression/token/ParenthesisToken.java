package io.github.kutify.math.expression.token;

public class ParenthesisToken extends AbstractToken {

    private final boolean isOpening;

    public ParenthesisToken(int position, boolean isOpening) {
        super(TokenType.PARENTHESIS, position);
        this.isOpening = isOpening;
    }

    public boolean isOpening() {
        return isOpening;
    }

    @Override
    public boolean equalsRegardlessPosition(Token o) {
        return o.getType() == TokenType.PARENTHESIS &&
            ((ParenthesisToken) o).isOpening == isOpening;
    }
}

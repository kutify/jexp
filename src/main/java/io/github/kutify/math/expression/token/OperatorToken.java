package io.github.kutify.math.expression.token;

public class OperatorToken extends AbstractToken {

    private final OperatorType operatorType;

    public OperatorToken(int position, OperatorType operatorType) {
        super(TokenType.OPERATOR, position);
        this.operatorType = operatorType;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public boolean hasHigherPrecedence(OperatorToken o) {
        return operatorType.hasHigherPrecedence(o.operatorType);
    }

    public boolean hasHigherOrEqualPrecedence(OperatorToken o) {
        return operatorType.hasHigherOrEqualPrecedence(o.operatorType);
    }

    @Override
    public boolean equalsRegardlessPosition(Token o) {
        return o.getType() == TokenType.OPERATOR &&
                ((OperatorToken) o).operatorType == operatorType;
    }
}

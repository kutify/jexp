package io.github.kutify.math.expression.token;

public enum OperatorType {
    PLUS(2),
    MINUS(2),
    DIVIDE(3),
    MOD(3),
    MULTIPLY(3),
    POWER(4);

    private final int precedence;

    OperatorType(int precedence) {
        this.precedence = precedence;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean hasHigherPrecedence(OperatorType o) {
        return precedence > o.precedence;
    }

    public boolean hasHigherOrEqualPrecedence(OperatorType o) {
        return precedence >= o.precedence;
    }
}

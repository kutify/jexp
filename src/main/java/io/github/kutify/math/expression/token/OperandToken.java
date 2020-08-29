package io.github.kutify.math.expression.token;

import java.util.Objects;

public class OperandToken extends AbstractToken {

    private final String value;

    public OperandToken(int position, String value) {
        super(TokenType.OPERAND, position);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equalsRegardlessPosition(Token o) {
        return o.getType() == TokenType.OPERAND &&
                Objects.equals(((OperandToken) o).value, value);
    }
}

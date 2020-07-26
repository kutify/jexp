package io.github.kutify.math.expression.operand;

import io.github.kutify.math.number.BigRational;

public class ValueOperand implements IValueOperand {

    private final BigRational value;

    public ValueOperand(BigRational value) {
        this.value = value;
    }

    @Override
    public BigRational getValue() {
        return value;
    }
}

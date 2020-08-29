package io.github.kutify.math.expression.operand;

public class ValueOperand<T> implements IValueOperand<T> {

    private final T value;

    public ValueOperand(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }
}

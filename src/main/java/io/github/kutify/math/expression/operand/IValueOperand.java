package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;

public interface IValueOperand<T> extends IOperand {

    @Override
    default Operation getOperation() {
        return Operation.VALUE;
    }

    T getValue();
}

package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;

public interface IValueOperand<T> extends IOperand {

    @Override
    default Operation getOperation() {
        return Operation.VALUE;
    }

    T getValue();
}

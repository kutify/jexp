package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;
import io.github.kutify.math.number.BigRational;

public interface IValueOperand extends IOperand {

    @Override
    default Operation getOperation() {
        return Operation.VALUE;
    }

    BigRational getValue();
}

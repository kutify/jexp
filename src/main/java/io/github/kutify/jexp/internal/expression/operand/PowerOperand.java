package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;

public class PowerOperand extends BinaryOperand {

    public PowerOperand(IOperand left, IOperand right) {
        super(Operation.POWER, left, right);
    }
}

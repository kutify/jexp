package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;

public class PowerOperand extends BinaryOperand {

    public PowerOperand(IOperand left, IOperand right) {
        super(Operation.POWER, left, right);
    }
}

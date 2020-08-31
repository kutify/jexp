package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;

public class ModOperand extends BinaryOperand {

    public ModOperand(IOperand left, IOperand right) {
        super(Operation.MOD, left, right);
    }
}

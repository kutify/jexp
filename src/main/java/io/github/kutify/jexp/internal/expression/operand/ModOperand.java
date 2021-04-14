package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;

public class ModOperand extends BinaryOperand {

    public ModOperand(IOperand left, IOperand right) {
        super(Operation.MOD, left, right);
    }
}

package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;

public class PlusOperand extends BinaryOperand {

    public PlusOperand(IOperand left, IOperand right) {
        super(Operation.PLUS, left, right);
    }
}

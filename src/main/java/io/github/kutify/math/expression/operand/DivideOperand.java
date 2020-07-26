package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;

public class DivideOperand extends BinaryOperand {

    public DivideOperand(IOperand left, IOperand right) {
        super(Operation.DIVIDE, left, right);
    }
}

package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;

public class MultiplyOperand extends BinaryOperand {

    public MultiplyOperand(IOperand left, IOperand right) {
        super(Operation.MULTIPLY, left, right);
    }
}

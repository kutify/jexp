package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;

public class MultiplyOperand extends BinaryOperand {

    public MultiplyOperand(IOperand left, IOperand right) {
        super(Operation.MULTIPLY, left, right);
    }
}

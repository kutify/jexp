package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;

public class DivideOperand extends BinaryOperand {

    public DivideOperand(IOperand left, IOperand right) {
        super(Operation.DIVIDE, left, right);
    }
}

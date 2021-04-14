package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;

public class PlusOperand extends BinaryOperand {

    public PlusOperand(IOperand left, IOperand right) {
        super(Operation.PLUS, left, right);
    }
}

package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;

public class MinusOperand extends BinaryOperand {

    public MinusOperand(IOperand left, IOperand right) {
        super(Operation.MINUS, left, right);
    }
}

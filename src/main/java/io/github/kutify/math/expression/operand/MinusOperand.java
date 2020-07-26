package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;

public class MinusOperand extends BinaryOperand {

    public MinusOperand(IOperand left, IOperand right) {
        super(Operation.MINUS, left, right);
    }
}

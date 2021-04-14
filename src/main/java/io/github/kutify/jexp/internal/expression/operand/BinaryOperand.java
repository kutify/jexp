package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;

abstract class BinaryOperand extends AbstractOperand implements IBinaryOperand {

    private final IOperand left;
    private final IOperand right;

    BinaryOperand(Operation operation, IOperand left, IOperand right) {
        super(operation);
        this.left = left;
        this.right = right;
    }

    @Override
    public Operation getOperation() {
        return this.operation;
    }

    @Override
    public IOperand getLeft() {
        return left;
    }

    @Override
    public IOperand getRight() {
        return right;
    }
}

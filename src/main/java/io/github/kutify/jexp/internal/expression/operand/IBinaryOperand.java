package io.github.kutify.jexp.internal.expression.operand;

public interface IBinaryOperand extends IOperand {

    IOperand getLeft();

    IOperand getRight();
}

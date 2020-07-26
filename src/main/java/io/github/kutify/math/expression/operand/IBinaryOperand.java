package io.github.kutify.math.expression.operand;

public interface IBinaryOperand extends IOperand {

    IOperand getLeft();

    IOperand getRight();
}

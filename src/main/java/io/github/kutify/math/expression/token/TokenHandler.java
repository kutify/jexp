package io.github.kutify.math.expression.token;

import io.github.kutify.math.expression.operand.IOperand;

import java.util.Deque;

public interface TokenHandler<T extends Token> {

    void handle(T token, Deque<IOperand> operandStack);
}

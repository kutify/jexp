package io.github.kutify.jexp.internal.expression.token;

import io.github.kutify.jexp.internal.expression.operand.IOperand;

import java.util.Deque;

public interface TokenHandler<T extends Token> {

    void handle(T token, Deque<IOperand> operandStack);
}

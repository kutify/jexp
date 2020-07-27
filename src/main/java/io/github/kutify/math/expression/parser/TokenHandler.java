package io.github.kutify.math.expression.parser;

import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.parser.token.Token;

import java.util.Deque;

public interface TokenHandler<T extends Token> {

    void handle(T token, Deque<IOperand> operandStack);
}

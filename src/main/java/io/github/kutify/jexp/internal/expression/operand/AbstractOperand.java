package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractOperand implements IOperand {

    protected final Operation operation;
}

package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractOperand implements IOperand {

    protected final Operation operation;
}

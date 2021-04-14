package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.internal.expression.operation.Operation;

public interface IVarOperand extends IOperand {

    @Override
    default Operation getOperation() {
        return Operation.VAR;
    }

    String getVarName();
}

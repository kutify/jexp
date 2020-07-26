package io.github.kutify.math.expression.operand;

import io.github.kutify.math.expression.operation.Operation;

public interface IVarOperand extends IOperand {

    @Override
    default Operation getOperation() {
        return Operation.VAR;
    }

    String getVarName();
}

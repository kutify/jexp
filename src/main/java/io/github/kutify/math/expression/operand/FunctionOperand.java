package io.github.kutify.math.expression.operand;

import io.github.kutify.math.api.Function;
import io.github.kutify.math.expression.operation.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FunctionOperand implements IOperand {
    private final Function function;
    private final List<IOperand> arguments;

    @Override
    public Operation getOperation() {
        return Operation.FUNCTION;
    }
}

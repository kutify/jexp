package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.api.Function;
import io.github.kutify.jexp.internal.expression.operation.Operation;
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

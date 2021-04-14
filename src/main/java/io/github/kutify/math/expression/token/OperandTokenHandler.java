package io.github.kutify.math.expression.token;

import io.github.kutify.math.api.Constant;
import io.github.kutify.math.exception.ExpressionSyntaxErrorType;
import io.github.kutify.math.exception.ExpressionSyntaxException;
import io.github.kutify.math.exception.VariableFormatException;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.operand.ValueOperand;
import io.github.kutify.math.expression.operand.VarOperand;

import java.util.Deque;
import java.util.Map;
import java.util.function.Supplier;

public abstract class OperandTokenHandler<T> implements TokenHandler<OperandToken> {

    private final Supplier<Map<String, Constant>> constantsProvider;

    protected OperandTokenHandler(Supplier<Map<String, Constant>> constantsProvider) {
        this.constantsProvider = constantsProvider;
    }

    @Override
    public void handle(OperandToken token, Deque<IOperand> operandStack) {
        String value = token.getValue();
        IOperand operand;
        if (Character.isDigit(value.charAt(0))) { // TODO Change it to proper validation
            try {
                operand = new ValueOperand<>(parseValue(value));
            } catch (NumberFormatException ex) {
                throw new ExpressionSyntaxException(null, ExpressionSyntaxErrorType.INVALID_NUMBER_FORMAT,
                    token.getPosition());
            }
        } else {
            Constant constant = constantsProvider.get().get(value);
            if (constant != null) {
                operand = new ValueOperand<>(getConstantValue(constant));
            } else {
                try {
                    operand = new VarOperand(value);
                } catch (VariableFormatException ex) {
                    throw new ExpressionSyntaxException(null, ExpressionSyntaxErrorType.INVALID_VARIABLE_FORMAT,
                        token.getPosition() + ex.getPosition());
                }
            }
        }
        operandStack.push(operand);
    }

    protected abstract T parseValue(String value);

    protected abstract T getConstantValue(Constant constant);
}

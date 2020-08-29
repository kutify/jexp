package io.github.kutify.math.expression.token;

import io.github.kutify.math.exception.ExpressionSyntaxErrorType;
import io.github.kutify.math.exception.ExpressionSyntaxException;
import io.github.kutify.math.exception.VariableFormatException;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.operand.ValueOperand;
import io.github.kutify.math.expression.operand.VarOperand;
import io.github.kutify.math.number.BigRational;

import java.util.Deque;

public class OperandTokenHandler implements TokenHandler<OperandToken> {

    @Override
    public void handle(OperandToken token, Deque<IOperand> operandStack) {
        String value = token.getValue();
        IOperand operand;
        if (Character.isDigit(value.charAt(0))) { // TODO Change it to proper validation
            try {
                operand = new ValueOperand(BigRational.parse(value));
            } catch (NumberFormatException ex) {
                throw new ExpressionSyntaxException(null, ExpressionSyntaxErrorType.INVALID_NUMBER_FORMAT,
                        token.getPosition());
            }
        } else {
            try {
                operand = new VarOperand(value);
            } catch (VariableFormatException ex) {
                throw new ExpressionSyntaxException(null, ExpressionSyntaxErrorType.INVALID_VARIABLE_FORMAT,
                        token.getPosition() + ex.getPosition());
            }
        }
        operandStack.push(operand);
    }
}

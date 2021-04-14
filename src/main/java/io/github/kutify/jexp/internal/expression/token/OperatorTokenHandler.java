package io.github.kutify.jexp.internal.expression.token;

import io.github.kutify.jexp.exception.ExpressionSyntaxErrorType;
import io.github.kutify.jexp.exception.ExpressionSyntaxException;
import io.github.kutify.jexp.internal.expression.operand.DivideOperand;
import io.github.kutify.jexp.internal.expression.operand.IOperand;
import io.github.kutify.jexp.internal.expression.operand.MinusOperand;
import io.github.kutify.jexp.internal.expression.operand.ModOperand;
import io.github.kutify.jexp.internal.expression.operand.MultiplyOperand;
import io.github.kutify.jexp.internal.expression.operand.PlusOperand;
import io.github.kutify.jexp.internal.expression.operand.PowerOperand;

import java.util.Deque;
import java.util.EnumMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public class OperatorTokenHandler implements TokenHandler<OperatorToken> {

    private final Map<OperatorType, BiFunction<IOperand, IOperand, IOperand>> mapper;

    public OperatorTokenHandler() {
        mapper = new EnumMap<>(OperatorType.class);
        mapper.put(OperatorType.PLUS, PlusOperand::new);
        mapper.put(OperatorType.MINUS, MinusOperand::new);
        mapper.put(OperatorType.MULTIPLY, MultiplyOperand::new);
        mapper.put(OperatorType.DIVIDE, DivideOperand::new);
        mapper.put(OperatorType.MOD, ModOperand::new);
        mapper.put(OperatorType.POWER, PowerOperand::new);
    }

    @Override
    public void handle(OperatorToken token, Deque<IOperand> operandStack) {
        OperatorType operatorType = token.getOperatorType();
        try {
            IOperand right = operandStack.pop();
            IOperand left = operandStack.pop();
            operandStack.push(mapper.get(operatorType).apply(left, right));
        } catch (NoSuchElementException ex) {
            throw new ExpressionSyntaxException(ExpressionSyntaxErrorType.WRONG_SYNTAX_AROUND_OPERATOR,
                token.getPosition());
        }
    }
}

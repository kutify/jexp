package io.github.kutify.math.expression;

import io.github.kutify.math.expression.operand.FunctionOperand;
import io.github.kutify.math.expression.operand.IBinaryOperand;
import io.github.kutify.math.expression.operand.IOperand;
import io.github.kutify.math.expression.operand.ValueOperand;
import io.github.kutify.math.expression.operand.VarOperand;
import io.github.kutify.math.expression.operation.Operation;
import io.github.kutify.math.func.Abs;
import io.github.kutify.math.func.Max;
import io.github.kutify.math.func.Min;
import io.github.kutify.math.func.Mult;
import io.github.kutify.math.func.Sqrt;
import io.github.kutify.math.func.Sum;
import io.github.kutify.math.number.BigRational;
import lombok.var;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Expression {

    private static final ExpressionContext DEFAULT_CONTEXT = new ExpressionContext();
    private static final ExpressionContext EMPTY_CONTEXT = new ExpressionContext();

    static {
        Arrays.asList(
                new Abs(),
                new Max(),
                new Min(),
                new Mult(),
                new Sqrt(),
                new Sum()
        ).forEach(DEFAULT_CONTEXT::registerFunction);
    }

    private final Function<Map<String, BigRational>, BigRational> calcFunction;

    public static Expression compile(String expression) {
        return DEFAULT_CONTEXT.parse(expression);
    }

    public static ExpressionContext newContext() {
        return new ExpressionContext(DEFAULT_CONTEXT);
    }

    public static ExpressionContext newEmptyContext() {
        return new ExpressionContext(EMPTY_CONTEXT);
    }

    Expression(IOperand rootOperand) {
        this.calcFunction = generateCalcFunction(rootOperand);
    }

    public BigRational calculate(Arguments arguments) {
        return calcFunction.apply(arguments.getArguments());
    }

    private static Function<Map<String, BigRational>, BigRational> generateCalcFunction(IOperand operand) {
        Operation operation = operand.getOperation();

        if (operation == Operation.VALUE) {
            return varValues -> ((ValueOperand) operand).getValue();

        } else if (operation == Operation.VAR) {
            return varValues -> varValues.get(((VarOperand) operand).getVarName());

        } else if (operand instanceof IBinaryOperand) {
            IBinaryOperand binaryOperand = (IBinaryOperand) operand;
            Function<Map<String, BigRational>, BigRational> leftFunc = generateCalcFunction(binaryOperand.getLeft());
            Function<Map<String, BigRational>, BigRational> rightFunc = generateCalcFunction(binaryOperand.getRight());
            if (operation == Operation.PLUS) {
                return varValues -> leftFunc.apply(varValues).add(rightFunc.apply(varValues));
            } else if (operation == Operation.MINUS) {
                return varValues -> leftFunc.apply(varValues).subtract(rightFunc.apply(varValues));
            } else if (operation == Operation.MULTIPLY) {
                return varValues -> leftFunc.apply(varValues).multiply(rightFunc.apply(varValues));
            } else if (operation == Operation.DIVIDE) {
                return varValues -> leftFunc.apply(varValues).divide(rightFunc.apply(varValues));
            } else if (operation == Operation.POWER) {
                return varValues -> leftFunc.apply(varValues).pow(rightFunc.apply(varValues));
            } else {
                throw new RuntimeException();
            }

        } else if (operand instanceof FunctionOperand) {
            FunctionOperand functionOperand = (FunctionOperand) operand;
            var function = functionOperand.getFunction();
            List<Function<Map<String, BigRational>, BigRational>> funcs = functionOperand.getArguments().stream()
                    .map(Expression::generateCalcFunction)
                    .collect(Collectors.toList());
            return varValues -> function.apply(
                    funcs.stream()
                            .map(func -> func.apply(varValues))
                            .collect(Collectors.toList())
            );
        } else {
            throw new RuntimeException();
        }
    }
}

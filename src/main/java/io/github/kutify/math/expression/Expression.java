package io.github.kutify.math.expression;

import io.github.kutify.math.func.Abs;
import io.github.kutify.math.func.Acos;
import io.github.kutify.math.func.Asin;
import io.github.kutify.math.func.Atan;
import io.github.kutify.math.func.Cbrt;
import io.github.kutify.math.func.Cos;
import io.github.kutify.math.func.Exp;
import io.github.kutify.math.func.Log;
import io.github.kutify.math.func.Log10;
import io.github.kutify.math.func.Max;
import io.github.kutify.math.func.Min;
import io.github.kutify.math.func.Mult;
import io.github.kutify.math.func.Signum;
import io.github.kutify.math.func.Sin;
import io.github.kutify.math.func.Sqrt;
import io.github.kutify.math.func.Sum;
import io.github.kutify.math.func.Tan;
import io.github.kutify.math.number.BigRational;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public class Expression<T> {

    private static final ExpressionContext DEFAULT_CONTEXT = new ExpressionContext();
    private static final ExpressionContext EMPTY_CONTEXT = new ExpressionContext();

    static {
        Arrays.asList(
                new Abs(), new Acos(), new Asin(), new Atan(), new Cbrt(), new Cos(), new Exp(), new Log(), new Log10(),
                new Max(), new Min(), new Mult(), new Signum(), new Sin(), new Sqrt(), new Sum(), new Tan()
        ).forEach(DEFAULT_CONTEXT::registerFunction);
    }

    private final Function<Map<String, T>, T> evalFunction;

    public static Expression<BigRational> compileRational(String expression) {
        return DEFAULT_CONTEXT.compileRational(expression);
    }

    public static Expression<Double> compileDouble(String expression) {
        return DEFAULT_CONTEXT.compileDouble(expression);
    }

    public static ExpressionContext newContext() {
        return new ExpressionContext(DEFAULT_CONTEXT);
    }

    public static ExpressionContext newEmptyContext() {
        return new ExpressionContext(EMPTY_CONTEXT);
    }

    Expression(Function<Map<String, T>, T> evalFunction) {
        this.evalFunction = evalFunction;
    }

    public T evaluate(Arguments<T> arguments) {
        return evalFunction.apply(arguments.getArguments());
    }
}

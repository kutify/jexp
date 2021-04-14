package io.github.kutify.math.expression;

import io.github.kutify.math.api.Arguments;
import io.github.kutify.math.api.Expression;

import java.util.Map;
import java.util.function.Function;

public class ExpressionImpl<T> implements Expression<T> {

//    private static final ExpressionContext DEFAULT_CONTEXT = new ExpressionContext();
//    private static final ExpressionContext EMPTY_CONTEXT = new ExpressionContext();
//
//    static {
//        Arrays.asList(
//                new Abs(), new Acos(), new Asin(), new Atan(), new Cbrt(), new Cos(), new Exp(), new Log(), new Log10(),
//                new Max(), new Min(), new Mult(), new Signum(), new Sin(), new Sqrt(), new Sum(), new Tan()
//        ).forEach(DEFAULT_CONTEXT::registerFunction);
//        Arrays.asList(
//                JExp.newConstant("Pi", Math.PI),
//                JExp.newConstant("E", Math.E)
//        ).forEach(DEFAULT_CONTEXT::registerConstant);
//    }

    private final Function<Map<String, T>, T> evalFunction;

//    public static ExpressionImpl<BigRational> compileRational(String expression) {
//        return DEFAULT_CONTEXT.compileRational(expression);
//    }
//
//    public static ExpressionImpl<Double> compileDouble(String expression) {
//        return DEFAULT_CONTEXT.compileDouble(expression);
//    }
//
//    public static ExpressionContext newContext() {
//        return new ExpressionContext(DEFAULT_CONTEXT);
//    }
//
//    public static ExpressionContext newEmptyContext() {
//        return new ExpressionContext(EMPTY_CONTEXT);
//    }

    ExpressionImpl(Function<Map<String, T>, T> evalFunction) {
        this.evalFunction = evalFunction;
    }

    @Override
    public T evaluate(Arguments<T> arguments) {
        return evalFunction.apply(arguments.getArguments());
    }
}

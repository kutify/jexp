package io.github.kutify.math.api;

import io.github.kutify.math.expression.ArgumentsImpl;
import io.github.kutify.math.expression.ConstantImpl;
import io.github.kutify.math.expression.ExpressionFactoryImpl;
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
import java.util.Collections;
import java.util.Map;

public class JExp {

    private static final ExpressionFactoryImpl DEFAULT_FACTORY;

    static {
        DEFAULT_FACTORY = new ExpressionFactoryImpl();

        Arrays.asList(
            new Abs(), new Acos(), new Asin(), new Atan(),
            new Cbrt(), new Cos(), new Exp(), new Log(),
            new Log10(), new Max(), new Min(), new Mult(),
            new Signum(), new Sin(), new Sqrt(), new Sum(),
            new Tan()
        )
            .forEach(DEFAULT_FACTORY::register);

        Arrays.asList(
            JExp.constant("Pi", Math.PI),
            JExp.constant("E", Math.E)
        )
            .forEach(DEFAULT_FACTORY::register);
    }

    private JExp() {
        throw new UnsupportedOperationException();
    }

    public static void register(Function function) {
        DEFAULT_FACTORY.register(function);
    }

    public static void register(Constant constant) {
        DEFAULT_FACTORY.register(constant);
    }

    public static ExpressionFactory getFactory() {
        return DEFAULT_FACTORY.clone();
    }

    public static ExpressionFactory getEmptyFactory() {
        return new ExpressionFactoryImpl();
    }

    public static Expression<Double> compile(String expression) {
        return DEFAULT_FACTORY.compile(expression);
    }

    public static Expression<Double> compileDouble(String expression) {
        return DEFAULT_FACTORY.compileDouble(expression);
    }


    // -----  Constants  --------------------------------------------------------------------------

    public static Expression<BigRational> compileRational(String expression) {
        return DEFAULT_FACTORY.compileRational(expression);
    }

    public static Constant constant(String name, double value) {
        return new ConstantImpl(name, value);
    }

    public static Constant constant(String name, BigRational value) {
        return new ConstantImpl(name, value);
    }


    // -----  Arguments  --------------------------------------------------------------------------

    public static Constant constant(String name, double doubleValue, BigRational rationalValue) {
        return new ConstantImpl(name, doubleValue, rationalValue);
    }

    public static ArgumentsBuilder<Double> argsBuilder() {
        return doubleArgsBuilder();
    }

    public static ArgumentsBuilder<Double> doubleArgsBuilder() {
        return new ArgumentsImpl.DoubleBuilder();
    }

    public static ArgumentsBuilder<BigRational> rationalArgsBuilder() {
        return new ArgumentsImpl.RationalBuilder();
    }

    public static <E> Arguments<E> emptyArgs() {
        return Collections::emptyMap;
    }

    public static Arguments<Double> args(Map<String, Double> args) {
        return doubleArgs(args);
    }

    public static Arguments<Double> doubleArgs(Map<String, Double> args) {
        return () -> args;
    }


    // --------------------------------------------------------------------------------------------

    public static Arguments<BigRational> rationalArgs(Map<String, BigRational> args) {
        return () -> args;
    }
}

package io.github.kutify.math.expression;

import io.github.kutify.math.number.BigRational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Arguments<T> {

    public static final Arguments<Double> EMPTY_DOUBLE = Arguments.doubleBuilder().build();
    public static final Arguments<BigRational> EMPTY_RATIONAL = Arguments.rationalBuilder().build();

    private final HashMap<String, T> arguments;

    private Arguments(Map<String, T> arguments) {
        this.arguments = new HashMap<>(arguments);
    }

    Map<String, T> getArguments() {
        return arguments;
    }

    static Builder<Double> doubleBuilder() {
        return new DoubleBuilder();
    }

    static Builder<BigRational> rationalBuilder() {
        return new RationalBuilder();
    }

    public static class DoubleBuilder extends Builder<Double> {

        @Override
        protected Double cast(long value) {
            return (double) value;
        }

        @Override
        protected Double cast(double value) {
            return value;
        }

        @Override
        protected Double cast(BigInteger value) {
            return value.doubleValue();
        }

        @Override
        protected Double cast(BigDecimal value) {
            return value.doubleValue();
        }

        @Override
        protected Double cast(BigRational value) {
            return value.doubleValue();
        }

        @Override
        protected Double cast(String value) {
            return Double.valueOf(value);
        }
    }

    public static class RationalBuilder extends Builder<BigRational> {

        @Override
        protected BigRational cast(long value) {
            return BigRational.valueOf(value);
        }

        @Override
        protected BigRational cast(double value) {
            return BigRational.parse(Double.valueOf(value).toString()); // TODO optimize
        }

        @Override
        protected BigRational cast(BigInteger value) {
            return BigRational.valueOf(value);
        }

        @Override
        protected BigRational cast(BigDecimal value) {
            return BigRational.valueOf(value);
        }

        @Override
        protected BigRational cast(BigRational value) {
            return value;
        }

        @Override
        protected BigRational cast(String value) {
            return BigRational.parse(value);
        }
    }

    public abstract static class Builder<T> {

        private final HashMap<String, T> arguments = new HashMap<>();

        public final Builder<T> with(String argName, long value) {
            arguments.put(argName, cast(value));
            return this;
        }

        public final Builder<T> with(String argName, double value) {
            arguments.put(argName, cast(value));
            return this;
        }

        public final Builder<T> with(String argName, BigInteger value) {
            arguments.put(argName, cast(value));
            return this;
        }

        public final Builder<T> with(String argName, BigDecimal value) {
            arguments.put(argName, cast(value));
            return this;
        }

        public final Builder<T> with(String argName, BigRational value) {
            arguments.put(argName, cast(value));
            return this;
        }

        public final Builder<T> with(String argName, String value) {
            arguments.put(argName, cast(value));
            return this;
        }

        public Arguments<T> build() {
            return new Arguments<>(arguments);
        }

        protected abstract T cast(long value);
        protected abstract T cast(double value);
        protected abstract T cast(BigInteger value);
        protected abstract T cast(BigDecimal value);
        protected abstract T cast(BigRational value);
        protected abstract T cast(String value);
    }
}

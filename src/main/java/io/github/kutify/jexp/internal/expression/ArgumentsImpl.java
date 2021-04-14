package io.github.kutify.jexp.internal.expression;

import io.github.kutify.jexp.api.Arguments;
import io.github.kutify.jexp.api.ArgumentsBuilder;
import io.github.kutify.jexp.api.BigRational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ArgumentsImpl<T> implements Arguments<T> {

    private final Map<String, T> arguments;

    private ArgumentsImpl(Map<String, T> arguments) {
        this.arguments = Collections.unmodifiableMap(arguments);
    }

    @Override
    public Map<String, T> getArguments() {
        return arguments;
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
            return BigRational.valueOf(value);
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

    public abstract static class Builder<T> implements ArgumentsBuilder<T> {

        private final Map<String, T> arguments = new HashMap<>();

        @Override
        public final Builder<T> with(String argName, long value) {
            arguments.put(argName, cast(value));
            return this;
        }

        @Override
        public final Builder<T> with(String argName, double value) {
            arguments.put(argName, cast(value));
            return this;
        }

        @Override
        public final Builder<T> with(String argName, BigInteger value) {
            arguments.put(argName, cast(value));
            return this;
        }

        @Override
        public final Builder<T> with(String argName, BigDecimal value) {
            arguments.put(argName, cast(value));
            return this;
        }

        @Override
        public final Builder<T> with(String argName, BigRational value) {
            arguments.put(argName, cast(value));
            return this;
        }

        @Override
        public final Builder<T> with(String argName, String value) {
            arguments.put(argName, cast(value));
            return this;
        }

        @Override
        public ArgumentsImpl<T> build() {
            return new ArgumentsImpl<>(arguments);
        }

        protected abstract T cast(long value);

        protected abstract T cast(double value);

        protected abstract T cast(BigInteger value);

        protected abstract T cast(BigDecimal value);

        protected abstract T cast(BigRational value);

        protected abstract T cast(String value);
    }
}

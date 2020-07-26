package io.github.kutify.math.expression;

import io.github.kutify.math.number.BigRational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Arguments {

    private final HashMap<String, BigRational> arguments;

    private Arguments(Map<String, BigRational> arguments) {
        this.arguments = new HashMap<>(arguments);
    }

    Map<String, BigRational> getArguments() {
        return arguments;
    }

    static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final HashMap<String, BigRational> arguments = new HashMap<>();

        public Builder with(String argName, long value) {
            arguments.put(argName, BigRational.valueOf(value));
            return this;
        }

        public Builder with(String argName, BigInteger value) {
            arguments.put(argName, BigRational.valueOf(value));
            return this;
        }

        public Builder with(String argName, BigDecimal value) {
            arguments.put(argName, BigRational.valueOf(value));
            return this;
        }

        public Builder with(String argName, BigRational value) {
            arguments.put(argName, value);
            return this;
        }

        public Builder with(String argName, String value) {
            arguments.put(argName, BigRational.parse(value));
            return this;
        }

        public Arguments build() {
            return new Arguments(arguments);
        }
    }
}

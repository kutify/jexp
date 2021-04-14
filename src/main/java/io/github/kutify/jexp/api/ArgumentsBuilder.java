package io.github.kutify.jexp.api;

import io.github.kutify.jexp.internal.expression.ArgumentsImpl;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface ArgumentsBuilder<T> {

    ArgumentsImpl<T> build();

    ArgumentsBuilder<T> with(String argName, long value);

    ArgumentsBuilder<T> with(String argName, double value);

    ArgumentsBuilder<T> with(String argName, BigInteger value);

    ArgumentsBuilder<T> with(String argName, BigDecimal value);

    ArgumentsBuilder<T> with(String argName, BigRational value);

    ArgumentsBuilder<T> with(String argName, String value);
}

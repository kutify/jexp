package io.github.kutify.math.api;

public interface Expression<T> {

    T evaluate(Arguments<T> arguments);
}

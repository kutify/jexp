package io.github.kutify.jexp.api;

public interface Expression<T> {

    T evaluate(Arguments<T> arguments);
}

package io.github.kutify.math.api;

import java.util.Map;

public interface Arguments<T> {

    Map<String, T> getArguments();
}

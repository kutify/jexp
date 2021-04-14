package io.github.kutify.jexp.api;

import java.util.Map;

public interface Arguments<T> {

    Map<String, T> getArguments();
}

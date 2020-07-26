package io.github.kutify.math.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VariableFormatException extends Exception {
    private final int position;
}

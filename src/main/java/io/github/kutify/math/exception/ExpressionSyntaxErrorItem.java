package io.github.kutify.math.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpressionSyntaxErrorItem {
    private final ExpressionSyntaxErrorType errorType;
    private final int position;
}

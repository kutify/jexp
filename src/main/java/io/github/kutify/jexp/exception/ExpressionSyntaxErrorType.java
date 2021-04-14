package io.github.kutify.jexp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpressionSyntaxErrorType {

    INVALID_SYMBOL("invalid symbol"),
    NO_OPENING_PARENTHESIS("opening parenthesis not found"),
    NO_CLOSING_PARENTHESIS("closing parenthesis not found"),
    INVALID_NUMBER_FORMAT("invalid number format"),
    UNDEFINED_FUNCTION("undefined function"),
    WRONG_FUNCTION_ARGS_NUMBER("wrong function arguments number"),
    WRONG_SYNTAX_AROUND_OPERATOR("wrong syntax around operator"),
    INVALID_VARIABLE_FORMAT("invalid variable format");

    private final String name;
}

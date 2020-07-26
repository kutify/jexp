package io.github.kutify.math.expression.parser;

import io.github.kutify.math.expression.parser.token.OperandToken;
import io.github.kutify.math.expression.parser.token.OperatorToken;
import io.github.kutify.math.expression.parser.token.OperatorType;
import io.github.kutify.math.expression.parser.token.ParenthesisToken;
import io.github.kutify.math.expression.parser.token.Token;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("getTestData")
    void test(int index, List<Token> expectedTokens, List<String> inputs) {
        for (String input : inputs) {
            List<Token> parsedTokens = Parser.parse(input);
            int expectedSize = expectedTokens.size();
            assertEquals(expectedSize, parsedTokens.size());
            for (int i = 0; i < expectedSize; i++) {
                assertTrue(
                        expectedTokens.get(i)
                                .equalsRegardlessPosition(parsedTokens.get(i)),
                        "Wrong result for '" + input + "'"
                );
            }
        }
    }

    private static Stream<Arguments> getTestData() {
        return Stream.of(
                Arguments.of(0,
                        Arrays.asList(operand("a"), operator('+'), operand("b")),
                        Arrays.asList("a+b", " a + b ", "a +b ", "a+ b", " a+b ", " a+b", "a+b ")
                ),
                Arguments.of(1,
                        Arrays.asList(operand("a1X_x"), operand("b")),
                        Arrays.asList("a1X_x b", "   a1X_x   b", "a1X_x   b   ", "a1X_x     b")
                )
        );
    }

    private static Token operand(String value) {
        return new OperandToken(0, value);
    }

    private static Token parenthesis(char parenthesis) {
        if (parenthesis == '(') {
            return new ParenthesisToken(0, true);
        }
        if (parenthesis == ')') {
            return new ParenthesisToken(0, false);
        }
        throw new IllegalArgumentException();
    }

    private static Token operator(char operator) {
        if (operator == '+') {
            return new OperatorToken(0 , OperatorType.PLUS);
        }
        if (operator == '-') {
            return new OperatorToken(0 , OperatorType.MINUS);
        }
        if (operator == '*') {
            return new OperatorToken(0 , OperatorType.MULTIPLY);
        }
        if (operator == '/') {
            return new OperatorToken(0 , OperatorType.DIVIDE);
        }
        if (operator == '^') {
            return new OperatorToken(0 , OperatorType.POWER);
        }
        throw new IllegalArgumentException();
    }
}

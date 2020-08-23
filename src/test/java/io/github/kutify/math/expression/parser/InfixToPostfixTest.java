package io.github.kutify.math.expression.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfixToPostfixTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("getTestData")
    void test(int index, String expected, Collection<String> inputs) {
        for (String input : inputs) {
            assertEquals(expected,
                    Serializer.serialize(
                            Parser.infixToPostfix(
                                    Parser.parseTokens(input)
                            )
                    ),
                    "Wrong result for '" + input + "'"
            );
        }
    }

    private static Stream<Arguments> getTestData() {
        return Stream.of(
                Arguments.of(0, "a",
                        Arrays.asList("a", "(a)", "((a))")),
                Arguments.of(1, "a b +",
                        Arrays.asList("a+b", "(a)+b", "a+(b)", "(a+b)")),
                Arguments.of(2, "a 2 * b 3 / +",
                        Arrays.asList("a*2 + b/3", "(((a)*(2)) + ((b)/(3)))"))
        );
    }
}

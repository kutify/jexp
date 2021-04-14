package io.github.kutify.jexp.internal.expression;

import io.github.kutify.jexp.api.JExp;
import io.github.kutify.jexp.exception.ExpressionSyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.of;

class SyntaxViolationTest {

    private static Stream<Arguments> getTestData() {
        return Stream.of(
            of("(a", "closing parenthesis not found at '(' (pos. 0)"),
            of("()", "some unrecognized errors"),
            of("1 + ()", "wrong syntax around operator at '+' (pos. 2)"),
            of("1 + ", "wrong syntax around operator at '+' (pos. 2)"),
            of(")", "opening parenthesis not found at ')' (pos. 0)"),
            of("a)", "opening parenthesis not found at ')' (pos. 1)"),
            of("1 + qwerty(123)", "undefined function at 'q' (pos. 4)"),
            of("1 + sqrt(1,2)", "wrong function arguments number at 's' (pos. 4)")
        );
    }

    @Test
    void test() {
        Arguments a = getTestData().collect(Collectors.toList()).get(3);
        test((String) a.get()[0], (String) a.get()[1]);
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(String expression, String expectedMessage) {
        Throwable throwable = assertThrows(
            ExpressionSyntaxException.class,
            () -> JExp.compileDouble(expression)
        );
        String actualMessage = throwable.getMessage();

        String expectedMessageStart = "Expression \"" + expression + "\" contains ";
        assertEquals(0, actualMessage.indexOf(expectedMessageStart), "Got: " + actualMessage);

        assertTrue(actualMessage.contains(expectedMessage), "Got: " + actualMessage);
    }
}

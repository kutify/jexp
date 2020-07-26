package io.github.kutify.math.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {

    @Test
    void test() {
        Expression expression = Expression.compile("(a/3)^2 + (b/3)^(-1)");

        assertEquals("29/18",
                expression.calculate(
                        Arguments.builder()
                                .with("a", 1)
                                .with("b", 2)
                                .build()
                ).toString()
        );
    }
}

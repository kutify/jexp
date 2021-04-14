package io.github.kutify.math.number;

import io.github.kutify.math.util.tuple.Four;
import lombok.var;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SoftAssertionsExtension.class)
class BigRationalTest {

    private static void checkItem(SoftAssertions softly,
                                  BigRational value,
                                  BigRational expectedValue,
                                  String expectedToString) {
        softly.assertThat(value.compareTo(expectedValue)).isEqualTo(0);
        softly.assertThat(value).isEqualTo(expectedValue);
        softly.assertThat(value.toString()).isEqualTo(expectedToString);
    }

    private static List<Four<String, BigRational, String, Boolean>> getTestList() {

        // String to parse, expected value, value.toString(), is equal to previous one
        List<Four<String, BigRational, String, Boolean>> list = new ArrayList<>();

        // Items are placed in ascending order
        list.add(Four.of("0", new BigRational(BigInteger.ZERO, BigInteger.TEN), "0", null));
        list.add(Four.of("0", BigRational.ZERO, "0", true));
        list.add(Four.of("00.00", BigRational.ZERO, "0", true));

        list.add(Four.of("1", new BigRational(BigInteger.TEN, BigInteger.TEN), "1", false));
        list.add(Four.of("1.0", BigRational.ONE, "1", true));
        list.add(Four.of("1.0/1.0", BigRational.ONE, "1", true));

        return list;
    }

    @Test
    void modOperationTest() {
        assertEquals(
            BigRational.parse("2/15"),
            BigRational.parse("7/3").mod(BigRational.parse("1/5"))
        );
    }

    @Test
    void test(SoftAssertions softly) {
        var testList = getTestList();

        BigRational prev = null;
        for (var testItem : testList) {
            BigRational next = BigRational.parse(testItem.getFirst());
            checkItem(softly, next, testItem.getSecond(), testItem.getThird());
            if (prev != null) {
                if (testItem.getFourth()) {
                    assertEquals(0, next.compareTo(prev));
                    assertEquals(prev, next);
                } else {
                    assertTrue(next.compareTo(prev) > 0);
                    assertNotEquals(prev, next);
                }
            }
            prev = next;
        }
    }
}

package io.github.kutify.math.number;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class BigRational extends Number implements Comparable<BigRational> {

    public static final BigRational ZERO = new BigRational(BigInteger.ZERO, BigInteger.ONE);
    public static final BigRational ONE = new BigRational(BigInteger.ONE, BigInteger.ONE);

    private static final String SLASH = "/";

    public static BigRational valueOf(long number) {
        return new BigRational(BigInteger.valueOf(number), BigInteger.ONE);
    }

    public static BigRational valueOf(BigInteger number) {
        return new BigRational(number, BigInteger.ONE);
    }

    public static BigRational valueOf(BigDecimal number) {
        number = number.stripTrailingZeros();
        int scale = Math.max(number.scale(), 0);
        return new BigRational(
                number.movePointRight(scale).toBigInteger(),
                BigInteger.TEN.pow(scale)
        );
    }

    public static BigRational parse(String value) {
        String[] numeratorAndDenominator = value.split(SLASH, -1);
        if (numeratorAndDenominator.length > 2) {
            throw new NumberFormatException();
        }
        if (numeratorAndDenominator.length == 1) {
            return BigRational.parseDecimal(numeratorAndDenominator[0]);
        } else {
            return BigRational.parseDecimal(numeratorAndDenominator[0])
                    .divide(BigRational.parseDecimal(numeratorAndDenominator[1]));
        }
    }

    private static BigRational parseDecimal(String decimal) {
        return valueOf(new BigDecimal(decimal));
    }

    private final BigInteger numerator;
    private final BigInteger denominator;

    public BigRational(BigInteger numerator, BigInteger denominator) {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Denominator can not be zero");
        }
        if (numerator.equals(BigInteger.ZERO)) {
            this.numerator = BigInteger.ZERO;
            this.denominator = BigInteger.ONE;
        } else {
            BigInteger cgd = numerator.gcd(denominator);
            numerator = numerator.divide(cgd);
            denominator = denominator.divide(cgd);
            boolean shouldNegate = denominator.compareTo(BigInteger.ZERO) < 0;
            this.numerator = shouldNegate ? numerator.negate() : numerator;
            this.denominator = shouldNegate ? denominator.negate() : denominator;
        }
    }

    private BigRational(BigInteger numerator, BigInteger denominator, boolean internal) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int signum() {
        return numerator.signum();
    }

    public BigRational negate() {
        return new BigRational(numerator.negate(), denominator, true);
    }

    public BigRational abs() {
        return new BigRational(numerator.abs(), denominator, true);
    }

    public BigRational add(BigRational o) {
        return new BigRational(
                numerator.multiply(o.denominator).add(o.numerator.multiply(denominator)),
                denominator.multiply(o.denominator)
        );
    }

    public BigRational subtract(BigRational o) {
        return new BigRational(
                numerator.multiply(o.denominator).subtract(o.numerator.multiply(denominator)),
                denominator.multiply(o.denominator)
        );
    }

    public BigRational multiply(BigRational o) {
        return new BigRational(
                numerator.multiply(o.numerator),
                denominator.multiply(o.denominator)
        );
    }

    public BigRational divide(BigRational o) {
        return new BigRational(
                numerator.multiply(o.denominator),
                denominator.multiply(o.numerator)
        );
    }

    public BigRational pow(int exponent) {
        if (exponent < 0) {
            exponent = -exponent;
            return new BigRational(denominator.pow(exponent), numerator.pow(exponent));
        } else {
            return new BigRational(numerator.pow(exponent), denominator.pow(exponent));
        }
    }

    public BigRational pow(BigRational exponent) {
        try {
            return pow(exponent.intValueExact());
        } catch (ArithmeticException ex) {
            return BigRational.valueOf(
                    new BigDecimal(
                            Math.pow(doubleValue(),exponent.doubleValue())
                    ).setScale(16, BigDecimal.ROUND_HALF_UP)
            );

        }
    }

    public boolean isInteger() {
        return denominator.compareTo(BigInteger.ONE) == 0;
    }

    @Override
    public int intValue() {
        return numerator.divide(denominator).intValue();
    }

    public int intValueExact() {
        if (isInteger()) {
            return numerator.divide(denominator).intValueExact();
        } else {
            throw new ArithmeticException("Value " + this + " is fractional");
        }
    }

    @Override
    public long longValue() {
        return numerator.divide(denominator).longValue();
    }

    public long longValueExact() {
        if (isInteger()) {
            return numerator.divide(denominator).longValueExact();
        } else {
            throw new ArithmeticException("Value " + this + " is fractional");
        }
    }

    @Override
    public float floatValue() {
        return numerator.floatValue() / denominator.floatValue();
    }

    @Override
    public double doubleValue() {
        return numerator.doubleValue() / denominator.doubleValue();
    }

    @Override
    public int compareTo(BigRational o) {
        return numerator.multiply(o.denominator)
                .compareTo(o.numerator.multiply(denominator));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigRational that = (BigRational) o;
        return Objects.equals(numerator, that.numerator) &&
                Objects.equals(denominator, that.denominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public String toString() {
        String result = numerator.toString();
        return BigInteger.ONE.equals(denominator) ? result : result + "/" + denominator.toString();
    }
}

package io.github.kutify.math.expression;

import io.github.kutify.math.api.Constant;
import io.github.kutify.math.number.BigRational;

public class ConstantImpl implements Constant {

    private final String name;
    private final double doubleValue;
    private final BigRational rationalValue;

    public ConstantImpl(String name,
                        double doubleValue,
                        BigRational rationalValue) {
        this.name = name;
        this.doubleValue = doubleValue;
        this.rationalValue = rationalValue;
    }

    public ConstantImpl(String name,
                        double doubleValue) {
        this(name, doubleValue, BigRational.valueOf(doubleValue));
    }

    public ConstantImpl(String name,
                        BigRational rationalValue) {
        this(name, rationalValue.doubleValue(), rationalValue);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getDoubleValue() {
        return doubleValue;
    }

    @Override
    public BigRational getRationalValue() {
        return rationalValue;
    }
}

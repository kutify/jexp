package io.github.kutify.math.func;

import io.github.kutify.math.expression.Function;
import io.github.kutify.math.number.BigRational;

import java.util.Iterator;
import java.util.List;

public class Max implements Function {

    @Override
    public String getName() {
        return "max";
    }

    @Override
    public Double applyDouble(List<Double> argValues) {
        return apply(argValues);
    }

    @Override
    public BigRational applyRational(List<BigRational> argValues) {
        return apply(argValues);
    }

    private <T extends Comparable<T>> T apply(List<T> argValues) {
        final int size = argValues.size();
        if (size == 0) {
            throw new ArithmeticException("Could not evaluate maximum value among empty argument list");
        }
        Iterator<T> it = argValues.iterator();
        T max = it.next();
        while (it.hasNext()) {
            T item = it.next();
            if (item.compareTo(max) > 0) {
                max = item;
            }
        }
        return max;
    }
}

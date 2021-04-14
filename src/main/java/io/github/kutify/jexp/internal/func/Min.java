package io.github.kutify.jexp.internal.func;

import io.github.kutify.jexp.api.Function;
import io.github.kutify.jexp.api.BigRational;

import java.util.Iterator;
import java.util.List;

public class Min implements Function {

    @Override
    public String getName() {
        return "min";
    }

    @Override
    public BigRational applyRational(List<BigRational> argValues) {
        return apply(argValues);
    }

    @Override
    public Double applyDouble(List<Double> argValues) {
        return apply(argValues);
    }

    private <T extends Comparable<T>> T apply(List<T> argValues) {
        final int size = argValues.size();
        if (size == 0) {
            throw new ArithmeticException("Could not evaluate minimum value among empty argument list");
        }
        Iterator<T> it = argValues.iterator();
        T min = it.next();
        while (it.hasNext()) {
            T item = it.next();
            if (item.compareTo(min) < 0) {
                min = item;
            }
        }
        return min;
    }
}

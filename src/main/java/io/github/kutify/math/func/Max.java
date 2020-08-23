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
    public BigRational apply(List<BigRational> argValues) {
        final int size = argValues.size();
        if (size == 0) {
            throw new ArithmeticException("Could not evaluate maximum value among empty argument list");
        }
        Iterator<BigRational> it = argValues.iterator();
        BigRational max = it.next();
        while (it.hasNext()) {
            BigRational item = it.next();
            if (item.compareTo(max) > 0) {
                max = item;
            }
        }
        return max;
    }
}

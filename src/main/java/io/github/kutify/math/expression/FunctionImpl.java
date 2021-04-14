package io.github.kutify.math.expression;

import io.github.kutify.math.api.Function;
import io.github.kutify.math.number.BigRational;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FunctionImpl implements Function {

    private final String name;
    private final int argsNumber;
    private final java.util.function.Function<List<BigRational>, BigRational> rationalFunction;
    private final java.util.function.Function<List<Double>, Double> doubleFunction;

    public FunctionImpl(String name,
                        int argsNumber,
                        java.util.function.Function<List<BigRational>, BigRational> rationalFunction,
                        java.util.function.Function<List<Double>, Double> doubleFunction) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(rationalFunction);
        Objects.requireNonNull(doubleFunction);
        this.name = name;
        this.argsNumber = argsNumber;
        this.rationalFunction = rationalFunction;
        this.doubleFunction = doubleFunction;
    }

    public FunctionImpl(String name,
                        int argsNumber,
                        java.util.function.Function<List<BigRational>, BigRational> rationalFunction) {
        this(
            name,
            argsNumber,
            rationalFunction,
            doubles -> rationalFunction.apply(
                doubles.stream()
                    .map(BigRational::valueOf)
                    .collect(Collectors.toList())
            )
                .doubleValue()
        );
    }

    public FunctionImpl(String name,
                        java.util.function.Function<List<Double>, Double> doubleFunction,
                        int argsNumber) {
        this(
            name,
            argsNumber,
            doubles -> BigRational.valueOf(
                doubleFunction.apply(
                    doubles.stream()
                        .map(BigRational::doubleValue)
                        .collect(Collectors.toList())
                )),
            doubleFunction
        );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getArgsNumber() {
        return argsNumber;
    }

    @Override
    public BigRational applyRational(List<BigRational> argValues) {
        return rationalFunction.apply(argValues);
    }

    @Override
    public Double applyDouble(List<Double> argValues) {
        return doubleFunction.apply(argValues);
    }
}

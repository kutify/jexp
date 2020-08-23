package io.github.kutify.math.expression;

import io.github.kutify.math.number.BigRational;

import java.util.List;

/**
 *
 */
public interface Function {

    /**
     * Returns the name of the function.
     * Return value mustn't be null.
     * @return function name
     */
    String getName();

    /**
     * Returns the number of function arguments if it is fixed, otherwise returns -1.
     * @return function arguments number or -1 if it is not fixed
     */
    default int getArgsNumber() {
        return -1;
    }

    /**
     * Applies the function to given argument values and returns the result.
     * @param argValues values of the arguments
     * @return result of the function evaluation
     */
    BigRational apply(List<BigRational> argValues);
}

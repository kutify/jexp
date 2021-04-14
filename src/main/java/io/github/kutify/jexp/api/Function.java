package io.github.kutify.jexp.api;

import java.util.List;

/**
 * Interface is used to define custom functions.
 */
public interface Function {

    /**
     * Returns the name of the function.
     * Return value mustn't be null.
     *
     * @return function name
     */
    String getName();

    /**
     * Returns the number of function arguments if it is fixed, otherwise returns -1.
     *
     * @return function arguments number or -1 if it is not fixed
     */
    default int getArgsNumber() {
        return -1;
    }

    /**
     * Applies the function to given argument values and returns the result.
     *
     * @param argValues values of the arguments
     * @return result of the function evaluation
     */
    BigRational applyRational(List<BigRational> argValues);

    /**
     * Applies the function to given argument values and returns the result.
     *
     * @param argValues values of the arguments
     * @return result of the function evaluation
     */
    Double applyDouble(List<Double> argValues);
}

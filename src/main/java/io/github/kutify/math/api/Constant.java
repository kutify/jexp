package io.github.kutify.math.api;

import io.github.kutify.math.number.BigRational;

/**
 * Interface is used to define custom constants.
 */
public interface Constant {

    /**
     * Returns the name of the constant.
     * Return value mustn't be null.
     *
     * @return constant name
     */
    String getName();

    /**
     * Returns this constant value.
     *
     * @return constant value
     */
    default BigRational getRationalValue() {
        return BigRational.valueOf(getDoubleValue());
    }

    /**
     * Returns this constant value.
     *
     * @return constant value
     */
    Double getDoubleValue();
}

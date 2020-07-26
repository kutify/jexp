package io.github.kutify.math.util.tuple;

import java.util.Objects;

public class Four<A, B, C, D> {

    private final A first;
    private final B second;
    private final C third;
    private final D fourth;

    public static <A, B, C, D> Four<A, B, C, D> of(A first, B second, C third, D fourth) {
        return new Four<>(first, second, third, fourth);
    }

    private Four(A first, B second, C third, D fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }

    public D getFourth() {
        return fourth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Four<?, ?, ?, ?> four = (Four<?, ?, ?, ?>) o;
        return Objects.equals(first, four.first) &&
                Objects.equals(second, four.second) &&
                Objects.equals(third, four.third) &&
                Objects.equals(fourth, four.fourth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third, fourth);
    }

    @Override
    public String toString() {
        return "Four{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                ", fourth=" + fourth +
                '}';
    }
}

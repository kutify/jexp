# Math expressions tool

The library enables you to write math expressions in convenient way and evaluate them using variables.

Let's start with simple example:
```
    Expression exp = Expression.compile("1 + 1");
    System.out.println(exp.calculate(Arguments.EMPTY)); // -> 2
```
And more sophisticated example:
```
    double d = 2.0/3 - 1 + 1.0/3;
    System.out.println(d); // -> -5.551115123125783E-17 (Almost zero, but not zero)
    
    Expression expression = Expression.compile("2/3 - 1 + 1/3");
    System.out.println(expression.calculate(Arguments.EMPTY)); // -> 0 (Exactly zero!)
```
As you can see, using ```Expression``` we can do precise math calculations.
It is possible because it process numbers using special class ```BigRational```
that represents fractions. So, the result of calculation is a ```BigRational``` number too:
```
    BigRational number = Expression.compile("5/7 + 7/5").calculate(Arguments.EMPTY);
    System.out.println(number); // -> 74/35
```
Likely you noticed that method ```calculate()``` accepts a single argument.
It's purpose is to provide calculation with values if your expression contains some variables.
Actually, in most cases you should use variables.
Variable values can be specified in different types.
Have a look at the example below:
```
    BigRational totalDistance = Expression.compile("initDist + initSpeed * time + (acceleration * time^2) / 2")
        .calculate(Arguments.builder()
            .with("initDist", 100)
            .with("initSpeed", "10")
            .with("time", new BigDecimal(5))
            .with("acceleration", BigInteger.valueOf(2))
            .build()
        );
    System.out.println(totalDistance); // -> 175
```
And some words about performance.
All costly things like string parsing take place inside ```compile()``` method.
So, compiled expression is literally a function that takes the arguments and does math operations.
We can explain it using pseudo code:
```
    Expression expression = Expression.compile("a ^ 2");
    
    // Under the hood expression has:
    Function<BigRational, BigRational> func = a -> Math.pow(a, 2);
```
So, for the sake of performance, you can use ```Expression``` as a static field.
```Expression``` instances are thread-safe.
```
public class MyClass {
    private static final Expression EXPRESSION = Expression.compile("a + b");
    
    public BigRational evaluate(int a, int b) {
        return EXPRESSION.calculate(Arguments.builder()
            .with("a", a)
            .with("b", b)
            .build()
        );
    }
}
```

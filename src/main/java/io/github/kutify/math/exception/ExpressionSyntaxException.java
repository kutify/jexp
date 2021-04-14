package io.github.kutify.math.exception;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
public class ExpressionSyntaxException extends RuntimeException {

    private final String expression;
    private final Collection<ExpressionSyntaxErrorItem> errorItems;

    public ExpressionSyntaxException(String expression, Collection<ExpressionSyntaxErrorItem> errorItems) {
        super(expression == null ? null : buildMessage(expression, errorItems));
        this.expression = expression;
        this.errorItems = errorItems;
    }

    public ExpressionSyntaxException(String expression, ExpressionSyntaxErrorType errorType, int position) {
        this(expression, Collections.singletonList(
            new ExpressionSyntaxErrorItem(errorType, position)
        ));
    }

    public ExpressionSyntaxException(ExpressionSyntaxErrorType errorType, int position) {
        this(null, Collections.singletonList(
            new ExpressionSyntaxErrorItem(errorType, position)
        ));
    }

    public ExpressionSyntaxException(String expression, Throwable ex) {
        super("Expression \"" + expression + "\" contains some unrecognized errors", ex);
        this.expression = expression;
        this.errorItems = Collections.emptyList();
    }

    private static String buildMessage(String expression, Collection<ExpressionSyntaxErrorItem> errorItems) {
        StringBuilder sb = new StringBuilder("Expression \"");
        sb.append(expression);
        sb.append("\" contains following errors:");
        for (ExpressionSyntaxErrorItem item : errorItems) {
            sb.append(' ');
            sb.append(item.getErrorType().getName());
            int position = item.getPosition();
            if (position > -1 && position < expression.length()) {
                sb.append(" at '");
                sb.append(expression.charAt(position));
                sb.append("' (pos. ");
                sb.append(position);
                sb.append(")");
            }
            sb.append(';');
        }
        int length = sb.length();
        sb.replace(length - 1, length, ".");
        return sb.toString();
    }
}

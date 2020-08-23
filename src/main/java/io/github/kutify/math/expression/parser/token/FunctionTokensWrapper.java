package io.github.kutify.math.expression.parser.token;

import java.util.List;

public class FunctionTokensWrapper extends AbstractToken {

    private final String functionName;
    private final List<List<Token>> args;

    public FunctionTokensWrapper(int position, String functionName, List<List<Token>> args) {
        super(TokenType.FUNCTION_TOKEN_WRAPPER, position);
        this.functionName = functionName;
        this.args = args;
    }

    @Override
    public boolean equalsRegardlessPosition(Token o) {
        return false;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<List<Token>> getArgs() {
        return args;
    }
}

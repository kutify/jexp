package io.github.kutify.jexp.internal.expression.operand;

import io.github.kutify.jexp.exception.VariableFormatException;

import java.util.regex.Pattern;

public class VarOperand implements IVarOperand {

    private static final Pattern pattern;

    static {
        pattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    private final String varName;

    public VarOperand(String varName) throws VariableFormatException {
        if (!pattern.matcher(varName).matches()) {
            throw new VariableFormatException(0);
        }
        this.varName = varName;
    }

    @Override
    public String getVarName() {
        return varName;
    }
}

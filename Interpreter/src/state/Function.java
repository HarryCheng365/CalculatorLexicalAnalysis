package state;

import type.StateType;
import type.ValuesType;

import java.util.Iterator;
import java.util.LinkedList;

public class Function extends State implements Iterable<Statement> {

    private FunctionSignature functionSignature;
    private ValuesType returnType;
    private LinkedList<Statement> statements;

    public Function(String functionName, ValuesType type) {
        stateType = StateType.FUNCTION;
        functionSignature = new FunctionSignature(functionName);
        returnType = type;
        statements = new LinkedList<Statement>();
    }

    public void addParameter(ValuesType type, String name) {
        functionSignature.addParameters(type, name);
    }

    public FunctionSignature getFunctionSignature() {
        return functionSignature;
    }

    public ValuesType getReturnType() {
        return returnType;
    }

    public void addStatement(Statement statement) {
        statements.addLast(statement);
    }

    @Override
    public Iterator<Statement> iterator() {
        return statements.iterator();
    }
}
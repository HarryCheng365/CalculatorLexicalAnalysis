package state;

import type.StatementType;
import type.StateType;


public abstract class Statement extends State {

    protected StatementType statementType;

    public Statement() {
        stateType = StateType.STATEMENT;
    }
}

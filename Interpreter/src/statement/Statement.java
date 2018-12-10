package statement;

import type.StatementType;

import type.StateType;


public abstract class Statement extends State {

    protected StatementType statementType;

    public Statement() {
        stateType = StateType.STATEMENT;
    }
    public Statement(StatementType type) {
    	this.statementType = type;
    }
    public StatementType getStatementType() {
    	return this.statementType;
    }
    public void setStatementType(StatementType stype) {
    	this.statementType=stype;
    }
   
}

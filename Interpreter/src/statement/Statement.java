package statement;

import type.StatementType;

import java.util.LinkedList;

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
    public abstract String display();
    
    public static String listDisplay(LinkedList<Statement> temp) {
    	LinkedList<Statement> sta=temp;
    	String result ="";
    	while(!sta.isEmpty()) {
    		result+=sta.poll().display();
    		result+="\n";
    	}
    	return result;
    	
    	
    }
   
}

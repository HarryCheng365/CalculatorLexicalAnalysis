package statement;

import type.StatementType;
import type.ValuesType;

import java.util.LinkedList;

import token.ExpressionToken;
import token.Values;

public class Initialization extends Statement {

    private String id;
    private ExpressionToken eToken;
    private LinkedList<ExpressionToken> elements;
    public Initialization() {
    	statementType = StatementType.INITIALIZATION;
    	elements = new LinkedList<>();
    }
    
    public Initialization(String id, ExpressionToken value){
        statementType = StatementType.INITIALIZATION;
        this.id = id;
        this.eToken = value;
        elements = new LinkedList<>();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
    	this.id= id;
    }

    public ExpressionToken getExpressionToken() {
    	return this.eToken;
    }
    public void setExpressionToken(ExpressionToken value) {
    	this.eToken=value;
    	
    }
    public void addElement(ExpressionToken expressionToken){
        elements.add(expressionToken);
    }

}

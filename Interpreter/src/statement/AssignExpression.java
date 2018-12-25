package statement;

import java.util.LinkedList;

import token.Assign;
import token.ExpressionToken;
import type.StatementType;

public class AssignExpression extends Statement {
	private String id;
    private ExpressionToken eToken;
    private Assign assign;
    private LinkedList<ExpressionToken> elements;
    
    public AssignExpression() {
    	statementType = StatementType.ASSIGN;
    }
    public AssignExpression(String id,Assign assign, ExpressionToken value){
        statementType = StatementType.INITIALIZATION;
        this.id = id;
        this.assign=assign;
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
    public Assign getAssign() {
    	return this.assign;
    }
    public void setAssign(Assign assign) {
    	this.assign=assign;
    }
    public void addElement(ExpressionToken expressionToken){
        elements.add(expressionToken);
    }
	@Override
	public String display() {
		return "<Assign Statement>\n"+this.id+" "+this.assign.display()+" "+this.eToken.display()+"<Assign Statement>\n";
	}
   

}

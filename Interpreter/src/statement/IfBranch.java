package statement;

import java.util.LinkedList;

import token.ExpressionToken;

public class IfBranch {
	LinkedList<Statement> ifBranch;
	ExpressionToken condition;
	public IfBranch() {
		ifBranch = new LinkedList<Statement>();
	}
	public IfBranch(ExpressionToken con, LinkedList<Statement> ifBran){
		this.ifBranch=ifBran;
		this.condition =con;
		
	}
	public ExpressionToken getCondition() {
	    return condition;
	}

	public void setCondition(ExpressionToken condition) {
	    this.condition = condition;
	}
	public LinkedList<Statement> getIfBranch() {
	    return ifBranch;
	}
	public void setElseBranch(LinkedList<Statement> elseBranch) {
		this.ifBranch=elseBranch;
		
	}

	public void setIfBranch(LinkedList<Statement> ifBranch) {
	    this.ifBranch = ifBranch;
	}

}

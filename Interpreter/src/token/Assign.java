package token;

import type.AssignType;
import type.TokenType;

public class Assign extends ExpressionToken {
	public AssignType assign;
	
	public Assign(AssignType temp) {
		token = TokenType.ASSIGN;
		assign = temp;
	}
	
	public String display() {
		if(assign == AssignType.ASSIGN)
			return String.format("<Assignment %c,line: %d,position: %d>",'=',this.getline(),this.getPos());
		if(assign == AssignType.ADDASSIGN)
			return String.format("<Assignment %s,line: %d,position: %d>","+=",this.getline(),this.getPos());
		if(assign == AssignType.SUBASSIGN)
			return String.format("<Assignment %s,line: %d,position: %d>","-=",this.getline(),this.getPos());
		if(assign == AssignType.MULASSIGN)
			return String.format("<Assignment %s,line: %d,position: %d>","*=",this.getline(),this.getPos());
		return "<UnKnownAssignment>";
		
	}
	
	public void setAssignType(AssignType temp) {
		assign = temp;
		
	}
	public AssignType getAssignType() {
		return this.assign;
	}
	public String print() {
		if(assign == AssignType.ASSIGN)
			return String.format("%c",'=');
		if(assign == AssignType.ADDASSIGN)
			return String.format("%s","+=");
		if(assign == AssignType.SUBASSIGN)
			return String.format("%s","-=");
		if(assign == AssignType.MULASSIGN)
			return String.format("%s","*=");
		return "<UnKnownAssignment>";
		
	}

}

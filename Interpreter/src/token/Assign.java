package token;

import type.AssignType;
import type.TokenType;

public class Assign extends Token {
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

}

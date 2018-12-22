package statement;

import java.util.LinkedList;

import statement.Statement;
import token.ExpressionToken;
import type.StatementType;

public class IfElse extends Statement {

LinkedList<IfBranch> childList;
IfBranch ifBranch;
LinkedList<Statement> elseBranch;

public IfElse(){
    statementType = StatementType.IFELSE;
    ifBranch = new IfBranch();
    childList = new LinkedList<IfBranch>();
}
public void setIfBranch(ExpressionToken condition, LinkedList<Statement> ifBran) {
	statementType = StatementType.IFELSE;
	ifBranch = new IfBranch();
	this.ifBranch.setCondition(condition);
	this.ifBranch.setIfBranch(ifBran);
	childList.add(ifBranch);
	
	
}
public void setElseBranch(LinkedList<Statement> elseBranch) {
    this.ifBranch.setElseBranch(elseBranch);
    this.childList.add(ifBranch);
}

public LinkedList<IfBranch> getIFELSE() {
    return this.childList;
}

public String ifBranchDisplay(LinkedList<IfBranch> temp) {
	LinkedList<IfBranch> temp2 =temp;
	String str ="";
	IfBranch ifb=temp2.poll();
	str+="if ("+ifb.getCondition().display()+")\n"+Statement.listDisplay(ifb.getIfBranch());
	while(!temp2.isEmpty()) {
		ifb = temp2.poll();
		if(ifb.condition==null) {
			str+="else\n "+Statement.listDisplay(ifb.getIfBranch());
			break;
		}	
		str+="else if("+ifb.getCondition().display()+")\n"+Statement.listDisplay(ifb.getIfBranch());
	}
	return str;
	
	
}
@Override
public String display() {
	return "<IFELSE Statement>\n"+this.ifBranchDisplay(this.getIFELSE())+"<IFELSE Statement>\n";
	// TODO Auto-generated method stub
}


}

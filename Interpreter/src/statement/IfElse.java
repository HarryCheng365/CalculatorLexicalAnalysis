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
}
public void setIfBranch(ExpressionToken condition, LinkedList<Statement> ifBran) {
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


}

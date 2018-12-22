package statement;

import type.StatementType;

public class While extends Loop {

    public While(){
        statementType = StatementType.WHILE;
    }
    
    public String display() {
    	return "<While Statement>"+"\n"+"Condition:\n "+this.condition.display()+"\n"+"LoopStatments:\n"+this.listDisplay(this.loopStatements)+"<While Statement>";
    
    }
}

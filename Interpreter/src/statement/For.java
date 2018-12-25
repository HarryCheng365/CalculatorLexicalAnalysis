package statement;

import statement.Loop;
import token.ExpressionToken;
import type.StatementType;
public class For extends Loop {

	 private AssignExpression init1;
	 private Initialization init2;
	 private AssignExpression incr;

	 public For(){
	        statementType = StatementType.FOR;
	    }

	public AssignExpression getAssignExpression() {
		return this.init1;
	}

	 public void setInit(AssignExpression init) {
	        this.init1 = init;
	    }
	 
	 public Initialization getInitialization() {
		 return this.init2;
	 }
	 public void setInitialization(Initialization init) {
		 this.init2=init;
	 }

	 public AssignExpression getIncr() {
	        return this.incr;
	    }

	 public void setIncr(AssignExpression incr) {
	        this.incr = incr;
	    }

	@Override
	public String display() {
		String temp ="<For Statement>"+"\n"+"Initialization:\n";
		if(this.init1!=null)
			temp+=this.init1.display();
		else if(this.init2!=null)
			temp+=this.init2.display();
		temp+="\nCondition:\n"+this.condition.display()+this.incr.display()+"<For Statement>";
		return temp;
	}
	}



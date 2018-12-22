package statement;

import statement.Loop;
import token.ExpressionToken;
import type.StatementType;
public class For extends Loop {

	 private ExpressionToken init;
	 private ExpressionToken incr;

	 public For(){
	        statementType = StatementType.FOR;
	    }

	 public ExpressionToken getInit() {
	        return init;
	    }

	 public void setInit(ExpressionToken init) {
	        this.init = init;
	    }

	 public ExpressionToken getIncr() {
	        return incr;
	    }

	 public void setIncr(ExpressionToken incr) {
	        this.incr = incr;
	    }

	@Override
	public String display() {
		return "<For Statement>"+"\n"+"Initialization:\n"+this.init.display();
	}
	}



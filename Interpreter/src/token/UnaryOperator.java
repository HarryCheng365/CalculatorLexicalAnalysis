package token;

import type.OperatorsType;

public class UnaryOperator extends Operators{
		private Token child;

	 	public UnaryOperator(OperatorsType type) {
	        super();
	        opeVal = type;
	    }


	    public void setChild(Token token){
	        this.child = token;
	    }

	    public Token getChild() {
	        return this.child;
	    }

}

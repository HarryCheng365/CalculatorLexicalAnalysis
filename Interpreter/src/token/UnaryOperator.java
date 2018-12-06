package token;

import type.OperatorsType;

public class UnaryOperator extends Operators{
		private Token child;

	 	public UnaryOperator(OperatorsType type) {
	        super();
	        opeVal = type;
	    }
	 	public UnaryOperator(Operators op) {
	 		super();
	 		this.opeVal=op.getOp();
	 		
	 	}


	   public void setChild(Token key) {
		   this.child =key;
	   }
	   public Token getChild() {
		   return this.child;
		   
	   }

}

package token;

import type.OperatorsType;

public class BinaryOperator extends Operators{
	private Token lChild;
    private Token rChild;

	public BinaryOperator(Operators temp) {
		super();
		opeVal=temp.opeVal;

		
		// TODO Auto-generated constructor stub
	}
	public BinaryOperator(OperatorsType temp) {
		super();
		opeVal=temp;

		
		// TODO Auto-generated constructor stub
	}
	 public void setlChild(Token token) {
	        lChild = token;
	    }

	 public void setrChild(Token token) {
	        rChild = token;
	    }

	    public Token getlChild() {
	        return lChild;
	    }

	    public Token getrChild() {
	        return rChild;
	    }

}

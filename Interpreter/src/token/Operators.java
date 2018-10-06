package token;

import type.OperatorsType;
import type.TokenType;


public class Operators extends Token{
	private OperatorsType opeVal;
	public Operators(OperatorsType temp) {
		token = TokenType.OPERATORS;
		opeVal = temp;
		
	}
	public String display() {
		if(opeVal == OperatorsType.ADD)
			return String.format("<Add-op,%d,line:,%d,position:,%d>",'+',this.getline(),this.getPos());
		if(opeVal == OperatorsType.SUBTRACT)
			return String.format("<Add-op,%d,line:,%d,position:,%d>",'-',this.getline(),this.getPos());
		if(opeVal == OperatorsType.MULTIPLY)
			return String.format("<Mul-op,%d,line:,%d,position:,%d>",'*',this.getline(),this.getPos());
		if(opeVal == OperatorsType.DIVISION)
			return String.format("<Mul-op,%d,line:,%d,position:,%d>",'/',this.getline(),this.getPos());
		if(opeVal == OperatorsType.MOD)
			return String.format("<Mul-op,%d,line:,%d,position:,%d>",'%',this.getline(),this.getPos());
		if(opeVal == OperatorsType.ASSIGN)
			return String.format("<Operator,%d,line:,%d,position:,%d>",'=',this.getline(),this.getPos());
		return "<UnKnownValue>";
	}
	public OperatorsType getOp() {
		return opeVal;
	}
}

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
			return String.format("<Add-op %c,line: %d,position: %d>",'+',this.getline(),this.getPos());
		if(opeVal == OperatorsType.SUBTRACT)
			return String.format("<Add-op %c,line: %d,position: %d>",'-',this.getline(),this.getPos());
		if(opeVal == OperatorsType.MULTIPLY)
			return String.format("<Mul-op %c,line: %d,position: %d>",'*',this.getline(),this.getPos());
		if(opeVal == OperatorsType.DIVISION)
			return String.format("<Mul-op %c,line: %d,position: %d>",'/',this.getline(),this.getPos());
		if(opeVal == OperatorsType.MOD)
			return String.format("<Mul-op %c,line: %d,position: %d>",'%',this.getline(),this.getPos());	
		if(opeVal == OperatorsType.AND)
			return String.format("<Bool-op %s,line: %d,position: %d>","&&",this.getline(),this.getPos());
		if(opeVal == OperatorsType.OR)
			return String.format("<Bool-op %s,line: %d,position: %d>","||",this.getline(),this.getPos());
		if(opeVal == OperatorsType.EQUAL)
			return String.format("<Bool-op %s,line: %d,position: %d>","==",this.getline(),this.getPos());
		if(opeVal == OperatorsType.UNEQUAL)
			return String.format("<Bool-op %s,line: %d,position: %d>","!=",this.getline(),this.getPos());
		if(opeVal == OperatorsType.LESSTHAN)
			return String.format("<Bool-op %s,line: %d,position: %d>","<",this.getline(),this.getPos());
		if(opeVal == OperatorsType.MORETHAN)
			return String.format("<Bool-op %s,line: %d,position: %d>",">",this.getline(),this.getPos());
		if(opeVal == OperatorsType.LESSTHANOREQUAL)
			return String.format("<Bool-op %s,line: %d,position: %d>","<=",this.getline(),this.getPos());
		if(opeVal == OperatorsType.MORETHANOREQUAL)
			return String.format("<Bool-op %s,line: %d,position: %d>",">=",this.getline(),this.getPos());
		
		return "<UnKnownOperator>";
	}
	public OperatorsType getOp() {
		return opeVal;
	}
	public void setOperatorsType(OperatorsType temp) {
		opeVal = temp;
	}
}

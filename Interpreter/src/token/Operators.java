package token;

import java.util.HashMap;

import type.OperatorsType;
import type.TokenType;


public class Operators extends ExpressionToken{
	protected OperatorsType opeVal;
    private static HashMap<OperatorsType, Integer> priorityMap = new HashMap<OperatorsType, Integer>();
	public Operators() {
		token = TokenType.OPERATORS;
		opeVal=OperatorsType.DEFAULT;
		
	}
	
	 static{
	        priorityMap.put(OperatorsType.NEGATIVESIGN,2);
	        priorityMap.put(OperatorsType.POSITIVEASIGN,2);
	        priorityMap.put(OperatorsType.NOT,2);
	        priorityMap.put(OperatorsType.DUAL_ADD,2);
	        priorityMap.put(OperatorsType.DUAL_SUBTRACT,2);
	        priorityMap.put(OperatorsType.MOD,3);
	        priorityMap.put(OperatorsType.DIVISION,3);
	        priorityMap.put(OperatorsType.MULTIPLY,3);
	        priorityMap.put(OperatorsType.ADD,4);
	        priorityMap.put(OperatorsType.SUBTRACT,4);
	        priorityMap.put(OperatorsType.LESSTHAN,5);
	        priorityMap.put(OperatorsType.LESSTHANOREQUAL,5);
	        priorityMap.put(OperatorsType.MORETHAN,5);
	        priorityMap.put(OperatorsType.MORETHANOREQUAL,5);
	        priorityMap.put(OperatorsType.EQUAL,6);
	        priorityMap.put(OperatorsType.UNEQUAL,6);
	        priorityMap.put(OperatorsType.AND,7);
	        priorityMap.put(OperatorsType.OR,7);

	    }
	public String display() {
		if(opeVal == OperatorsType.ADD)
			return String.format("<Add-op %c,line: %d,position: %d>",'+',this.getline(),this.getPos());
		if(opeVal == OperatorsType.SUBTRACT)
			return String.format("<Add-op %c,line: %d,position: %d>",'-',this.getline(),this.getPos());
		if(opeVal == OperatorsType.DUAL_ADD)
			return String.format("<DUAL-op %c,line: %d,position: %d>",'+',this.getline(),this.getPos());
		if(opeVal == OperatorsType.DUAL_SUBTRACT)
			return String.format("<DUAL-op %c,line: %d,position: %d>",'-',this.getline(),this.getPos());		
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
	public static boolean isPrioriThan(Operators op1, Operators op2) {
        return priorityMap.get(op1.getOp()) < priorityMap.get(op2.getOp());
    }
	public String print() {
		if(opeVal == OperatorsType.ADD)
			return String.format("%c",'+');
		if(opeVal == OperatorsType.SUBTRACT)
			return String.format("%c",'-');
		if(opeVal == OperatorsType.DUAL_ADD)
			return String.format("%c%c",'+','+');
		if(opeVal == OperatorsType.DUAL_SUBTRACT)
			return String.format("%c%c",'-','-');		
		if(opeVal == OperatorsType.MULTIPLY)
			return String.format("%c",'*');
		if(opeVal == OperatorsType.DIVISION)
			return String.format("%c",'/');
		if(opeVal == OperatorsType.MOD)
			return String.format("%c",'%');	
		if(opeVal == OperatorsType.AND)
			return String.format("%s","&&");
		if(opeVal == OperatorsType.OR)
			return String.format("%s","||");
		if(opeVal == OperatorsType.EQUAL)
			return String.format("%s","==");
		if(opeVal == OperatorsType.UNEQUAL)
			return String.format("%s","!=");
		if(opeVal == OperatorsType.LESSTHAN)
			return String.format("%s","<");
		if(opeVal == OperatorsType.MORETHAN)
			return String.format("%s",">");
		if(opeVal == OperatorsType.LESSTHANOREQUAL)
			return String.format("%s","<=");
		if(opeVal == OperatorsType.MORETHANOREQUAL)
			return String.format("%s",">=");
		return "<UnKnownOperator>";
	}
}

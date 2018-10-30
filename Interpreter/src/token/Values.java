package token;

import type.TokenType;
import type.ValuesType;

public class Values extends Token {
	private ValuesType valueType;
	private int intVal;
	private boolean boolVal;
	private double doubleVal;
	private String strVal;
	private char charVal;
	
	public Values(ValuesType temp) {
		token = TokenType.VALUES;
		valueType = temp;
	}
	
	public int getIntVal() {
		return intVal;
	}
	public void setIntValue(int temp) {
		this.intVal = temp;
	}
	
	public boolean getBool() {
		return boolVal;
	}
	
	public void setBoolVal(Boolean temp) {
		this.boolVal = temp;
		
	}
	
	public double getDouble() {
		return doubleVal;
	}
	
	public void setDouble(double temp) {
		this.doubleVal=temp;
	}
	
	public void setStr(String temp) {
		this.strVal = temp;
	}
	
	public String getStr() {
		return this.strVal;
	}
	
	public void setChar(char temp) {
		this.charVal=temp;
	}
	
	public char getChar() {
		return this.charVal;
	}
	
	public void setType(ValuesType temp) {
		valueType = temp;
	}
	public String display() {
		if(valueType == ValuesType.INTEGER)
			return String.format("<IntValue %d,line: %d,position: %d>",intVal,this.getline(),this.getPos());
		if(valueType == ValuesType.BOOLEAN)
			return String.format("<BoolValue %d,line: %d,position: %d>",boolVal,this.getline(),this.getPos());
		if(valueType == ValuesType.DOUBLE)
			return String.format("<DoubleValue %f,line: %d,position: %d>",doubleVal,this.getline(),this.getPos());
		if(valueType == ValuesType.CHAR)
			return String.format("<CharValue %c,line: %d,position: %d>",charVal,this.getline(),this.getPos());
		if(valueType == ValuesType.STRING)
			return String.format("<StringValue %s,line: %d,position: %d>",strVal,this.getline(),this.getPos());
		return "<UnKnownValue>";
		
	}

}

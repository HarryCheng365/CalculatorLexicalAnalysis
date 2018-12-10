package token;

import java.util.ArrayList;
import java.util.List;

import type.TokenType;
import type.ValuesType;

public class Values extends ExpressionToken {
	private ValuesType valueType;
	private int intVal;
	private boolean boolVal;
	private double doubleVal;
	private String strVal;
	private char charVal;
	private Integer key;
	
	
	public Values(ValuesType temp) {
		token = TokenType.VALUES;
		valueType = temp;
		key = new Integer(0);

		
	}
	public Integer getInteger() {
		return this.key;
	}
	
	public void setInteger(Integer key) {
		this.key=key;
		
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
	public ValuesType getType() {
		return this.valueType;
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
		if(valueType == ValuesType.VOID)
			return String.format("<Void %s,line: %d,position: %d>","void",this.getline(),this.getPos());
		return "<UnKnownValue>";
		
	}
	public String print() {
		if(valueType == ValuesType.INTEGER)
			return String.format("%d",intVal);
		if(valueType == ValuesType.BOOLEAN)
			return String.format("%d",boolVal);
		if(valueType == ValuesType.DOUBLE)
			return String.format("%f",doubleVal);
		if(valueType == ValuesType.CHAR)
			return String.format("%c",charVal);
		if(valueType == ValuesType.STRING)
			return String.format("%s",strVal);
		return "<UnKnownValue>";
	}

}

package token;

import type.TokenType;
import type.ValuesType;

public class Values extends Token {
	private ValuesType valueType;
	private int intVal;
	private boolean boolVal;
	private double doubleVal;
	
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
	
	public void setType(ValuesType temp) {
		valueType = temp;
	}
	public String display() {
		if(valueType == ValuesType.INTEGER)
			return String.format("<IntValue,%d,line:,%d,position:,%d>",intVal,this.getline(),this.getPos());
		if(valueType == ValuesType.BOOLEAN)
			return String.format("<BoolValue,%d,line:,%d,position:,%d>",boolVal,this.getline(),this.getPos());
		if(valueType == ValuesType.DOUBLE)
			return String.format("<DoubleValue,%f,line:,%d,position:,%d>",doubleVal,this.getline(),this.getPos());
		return "<UnKnownValue>";
		
	}

}

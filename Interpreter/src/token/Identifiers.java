package token;

import type.TokenType;

public class Identifiers extends Token{
	private String id;
	
	public Identifiers() {
		token = TokenType.IDENTIFIERS;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String temp) {
		this.id = temp;
	}
	
	public String display() {
		return String.format("<Identifier %s,line: %d,position: %d>",id,this.getline(),this.getPos());
	}

}

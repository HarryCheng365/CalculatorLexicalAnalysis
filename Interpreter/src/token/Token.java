package token;

import type.TokenType;


public abstract class Token {
	private int line;
	private int position;
	protected TokenType token;

	public int getline() {
		return line;
	}
	
	public void setLine(int temp) {
		this.line = temp;
	}
	public int getPos() {
		return position;
	}
	public void setPos(int temp) {
		
		this.position = temp;
	}
	public void setToken(TokenType temp) {
		this.token = temp;
	}
	public TokenType getToken() {
		return token;
		
	}
	public abstract String display();

}

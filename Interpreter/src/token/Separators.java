package token;

import type.SeparatorsType;
import type.TokenType;

public class Separators extends Token{
	private SeparatorsType sepVal;
	public Separators(SeparatorsType temp) {
		token = TokenType.SEPARATORS;
		sepVal = temp;
	}
	
	public String display() {
		if(sepVal == SeparatorsType.LEFTPARENTHESES)
			return String.format("<LeftParentheses %c,line: %d,position: %d>",'(',this.getline(),this.getPos());
		if(sepVal == SeparatorsType.RIGHTPARENTHESES)
			return String.format("<RightParentheses %c,line: %d,position: %d>",')',this.getline(),this.getPos());
		if(sepVal == SeparatorsType.SEMICOLON)
			return String.format("<Semicolon %c,line: %d,position: %d>",';',this.getline(),this.getPos());
		return "<UnKnownValue>";
	}
	
	public SeparatorsType getSep() {
		return sepVal;
	}
}

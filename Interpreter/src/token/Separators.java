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
		if(sepVal == SeparatorsType.LEFTBRACE)
			return String.format("<LeftBrace %c,line: %d,position: %d>",'{',this.getline(),this.getPos());
		if(sepVal == SeparatorsType.RIGHTBRACE)
			return String.format("<RightBrace %c,line: %d,position: %d>",'}',this.getline(),this.getPos());
		if(sepVal == SeparatorsType.LEFTBRACKET)
			return String.format("<LeftBracket %c,line: %d,position: %d>",'[',this.getline(),this.getPos());
		if(sepVal == SeparatorsType.RIGHTBRACKET)
			return String.format("<RightBracket %c,line: %d,position: %d>",']',this.getline(),this.getPos());
		if(sepVal == SeparatorsType.SEMICOLON)
			return String.format("<Semicolon %c,line: %d,position: %d>",';',this.getline(),this.getPos());
		if(sepVal == SeparatorsType.COLON)
			return String.format("<Colon %c line: %d,position: %d>",':',this.getline(),this.getPos());
		if(sepVal == SeparatorsType.COMMA)
			return String.format("<COMMA %c,line: %d,position: %d>",',',this.getline(),this.getPos());
		/*if(sepVal == SeparatorsType.DOUBLEQM)
			return String.format("<DoubleQuotationMark %c,line: %d,position: %d>",'\"',this.getline(),this.getPos());*/
		return "<UnKnownSeparator>";
	}
	
	public SeparatorsType getSep() {
		return sepVal;
	}
}

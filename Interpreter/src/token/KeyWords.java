package token;

import type.KeywordType;
import type.TokenType;

public class KeyWords extends Token {
	private KeywordType key;
	
	public KeyWords(KeywordType temp) {
		token = TokenType.KEYWORDS;
		key = temp;
		
	}
	
	public String display() {
		if(key == KeywordType.IF)
			return String.format("<Keyword %s,line: %d,position: %d>","if",this.getline(),this.getPos());
		if(key == KeywordType.ELSE)
			return String.format("<Keyword %s,line: %d,position: %d>","else",this.getline(),this.getPos());
		if(key == KeywordType.WHILE)
			return String.format("<Keyword %s,line: %d,position: %d>","while",this.getline(),this.getPos());
		if(key == KeywordType.FOR)
			return String.format("<Keyword %s,line: %d,position: %d>","for",this.getline(),this.getPos());
		if(key == KeywordType.INT)
			return String.format("<Keyword %s,line: %d,position: %d>","int",this.getline(),this.getPos());
		if(key == KeywordType.BOOL)
			return String.format("<Keyword %s,line: %d,position: %d>","bool",this.getline(),this.getPos());
		if(key == KeywordType.DOUBLE)
			return String.format("<Keyword %s,line: %d,position: %d>","double",this.getline(),this.getPos());
		if(key == KeywordType.CHAR)
			return String.format("<Keyword %s,line: %d,position: %d>","char",this.getline(),this.getPos());
		if(key == KeywordType.STRING)
			return String.format("<Keyword %s,line: %d,position: %d>","String",this.getline(),this.getPos());
		if(key == KeywordType.TRUE)
			return String.format("<Keyword %s,line: %d,position: %d>","true",this.getline(),this.getPos());
		if(key == KeywordType.FALSE)
			return String.format("<Keyword %s,line: %d,position: %d>","false",this.getline(),this.getPos());
		if(key == KeywordType.PRINT)
			return String.format("<Keyword %s,line: %d,position: %d>","print",this.getline(),this.getPos());
		
		return "<UnKnownKeyword>";
	}

}

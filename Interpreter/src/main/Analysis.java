package main;

import java.util.LinkedList;

import token.Operators;
import token.Separators;
import token.Token;
import type.OperatorsType;
import type.SeparatorsType;

public class Analysis {

	private Input input;
	
	private LinkedList<Token> tokens;
	
	public Analysis(Input temp) {
		input = temp;
		tokens = new LinkedList<Token>();
		
	}
	public void Lexical() {
		while(!input.isEnd()) {
			input.next();
			while(isBlank(input.readCh())&&!input.isEnd())
				input.next();
			// separator
			Separators temp1 = findSeparators();
			if(temp1!=null) {
				tokens.add(temp1);
				continue;
			}
			//operator
			Operators temp2 = findOperators();
			if(temp2!=null) {
				tokens.add(temp2);
				continue;
			}
			
			
			
		}
	}
	
	public boolean isBlank(int ch) {
		if(ch == -1 || ch == '\t' || ch == '\n' || ch == ' ')
			return true;
		return false;
	}
	public Separators findSeparators() {
		Separators temp;
		switch(input.readCh()) {
		case '(':
			temp = new Separators(SeparatorsType.LEFTPARENTHESES);
			break;
		case ')':
			temp = new Separators(SeparatorsType.RIGHTPARENTHESES);
			break;
		case ';':
			temp = new Separators(SeparatorsType.SEMICOLON);
			break;
		default:
			temp = null;
			return null;
		
		}
		temp.setLine(input.getLine());
		temp.setPos(input.getPosition());
		input.next();
		return temp;
		
	}
	
	public Operators findOperators() {
		Operators temp;
		switch(input.readCh()){
		case '+':
			temp = new Operators(OperatorsType.ADD);
			break;
		case '-':
			temp = new Operators(OperatorsType.SUBTRACT);
			break;
		case '*':
			temp = new Operators(OperatorsType.MULTIPLY);
			break;
		case '/':
			temp = new Operators(OperatorsType.DIVISION);
			break;
		case '%':
			temp = new Operators(OperatorsType.MOD);
			break;
		case '=':
			input.next();
			if(input.readCh()!='=')
				temp = new Operators(OperatorsType.ASSIGN);
			else {
				temp = null;
				return null;
			}
			input.previous();
			break;
		default:
			temp = null;
			return null;
		}
		
		temp.setLine(input.getLine());
		temp.setPos(input.getPosition());
		input.next();
		return temp;
	}
}

package main;

import java.util.LinkedList;
import exception.SyntaxException;
import token.Assign;
import token.Identifiers;
import token.Operators;
import token.Separators;
import token.Token;
import token.Values;
import type.AssignType;
import type.OperatorsType;
import type.SeparatorsType;
import type.ValuesType;

public class Analysis {

	private Input input;
	
	private LinkedList<Token> tokens;
	
	public Analysis(Input temp) {
		input = temp;
		tokens = new LinkedList<Token>();
		
	}
	public void Lexical() throws SyntaxException{
		while(!input.isEnd()) {			
			System.out.println(input.readCh());
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
			Assign temp3 = findAssign();
			if(temp3!=null) {
				tokens.add(temp3);
				continue;
			}
			//value(Integer)
			Values temp4 = findValues();
			if(temp4!=null) {
				tokens.add(temp4);
				continue;
			}
			
			throw new SyntaxException(input.getLine(),input.getPosition(),"invalid token!");
			
		}
		
		if(input.isEnd()){
			Separators temp1 = findSeparators();
			if(temp1!=null){
				System.out.println(input.readCh());
				if(temp1.getSep()==SeparatorsType.SEMICOLON) {
					tokens.add(temp1);
					return;
				}
			}
			throw new SyntaxException(input.getLine(),input.getPosition(),"End without Semicolon!");
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
		case '{':
			temp = new Separators(SeparatorsType.LEFTBRACE);
			break;
		case '}':
			temp = new Separators(SeparatorsType.RIGHTBRACE);
			break;
		case '[':
			temp = new Separators(SeparatorsType.LEFTBRACKET);
			break;
		case ']':
			temp = new Separators(SeparatorsType.RIGHTBRACKET);
			break;
		case ':':
			temp = new Separators(SeparatorsType.COLON);
			break;
		case '\'':
			temp = new Separators(SeparatorsType.SINGLEQM);
			break;
		case '\"':
			temp = new Separators(SeparatorsType.DOUBLEQM);
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
	public Assign findAssign() {
		Assign temp;
		temp = new Assign(AssignType.DEFAULT);
		switch(input.readCh()) {
		case '=':
			input.next();
			if(input.readCh() != '=')
				temp = new Assign(AssignType.ASSIGN);
			else 
				return null;
			input.previous();
			break;	
		case '+':
			if(!isCompound(temp,'=',AssignType.ADDASSIGN)) 		
				return null;		
			break;
		case '-':
			if(!isCompound(temp,'=',AssignType.SUBASSIGN)) 		
				return null;		
			break;
		case '*':
			if(!isCompound(temp,'=',AssignType.MULASSIGN)) 		
				return null;		
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
		temp = new Operators(OperatorsType.DEFAULT);
		switch(input.readCh()){
		case '+':
			if(!isNotCompound(temp,'=',OperatorsType.ADD)) 		
				return null;
			break;
		case '-':
			if(!isNotCompound(temp,'=',OperatorsType.SUBTRACT)) 		
				return null;
			break;
		case '*':
			if(!isNotCompound(temp,'=',OperatorsType.MULTIPLY)) 		
				return null;		
			break;
		case '/':
			temp = new Operators(OperatorsType.DIVISION);
			break;
		case '>':
			isCompound(temp,OperatorsType.MORETHAN,OperatorsType.MORETHANOREQUAL);
			break;
		case '<':
			isCompound(temp,OperatorsType.LESSTHAN,OperatorsType.LESSTHANOREQUAL);
			break;
		case '=':
			if(!isCompound(temp,'=',OperatorsType.EQUAL)) 		
				return null;		
			break;
		case '!':
			if(!isCompound(temp,'=',OperatorsType.UNEQUAL)) 		
				return null;		
			break;
		case '&':
			if(!isCompound(temp,'&',OperatorsType.AND)) 		
				return null;
			break;
		case '|':
			if(!isCompound(temp,'|',OperatorsType.OR)) 		
				return null;		
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
	
	public void isCompound(Operators temp,OperatorsType otype1,OperatorsType otype2) {
		input.next();
		if(input.readCh() == '=') {
			temp.setOperatorsType(otype2);
		}
		else {
			temp.setOperatorsType(otype1);
			input.previous();
		}
	}
	public boolean isCompound(Operators temp,char aim,OperatorsType otype) {
		input.next();
		if(input.readCh() == aim) {
			temp.setOperatorsType(otype);
			return true;
		}
		input.previous();
		return false;
	}
	public boolean isCompound(Assign temp,char aim,AssignType atype) {
		input.next();
		if(input.readCh() == aim) {
			temp.setAssignType(atype);
			return true;
		}
		input.previous();
		return false;
	}
	public boolean isNotCompound(Operators temp,char aim,OperatorsType otype) {
		input.next();
		if(input.readCh() != aim) {
			temp.setOperatorsType(otype);
			input.previous();
			return true;
		}
		input.previous();
		return false;
	}
	
	public Values findValues() {
		Values temp;
		int intVal;
		if(Character.isDigit(input.readCh())) {
			temp = new Values(ValuesType.INTEGER);
			intVal = 0;
			temp.setLine(input.getLine());
			temp.setPos(input.getPosition());
			while(!input.isEnd()&&(Character.isDigit(input.readCh())||input.readCh()=='.')) {
				if(input.readCh()=='.')
					break;
				intVal += input.readCh()-48;
				intVal *= 10;
				input.next();
			
			}
			intVal = intVal/10;
			if(input.readCh()=='.')
				return processDouble(intVal,temp);
			temp.setIntValue(intVal);
			return temp;
			
		}
		temp = null;
		return null;
		
	}
	
	public Values processDouble(int intVal, Values temp2) {
		double tail;
		int count;
		double doub;
		tail = 0;
		count = 0;
		input.next();
		while(Character.isDigit(input.readCh())){
			if(input.isEnd())
				break;
			tail +=input.readCh()-48;
			tail *=10;
			count++;
			input.next();
		}
		while(count>=0) {
		tail = tail/10;
		count --;
		}
		doub = intVal+tail;
		temp2.setType(ValuesType.DOUBLE);
		temp2.setDouble(doub);
		return temp2;	
	}
	
	public Identifiers findIdentifier() {
		return null;
	}
	
	public void print() {
		while(!tokens.isEmpty())
		System.out.println(tokens.pollFirst().display());
	}
}

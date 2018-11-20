package main;

import java.util.LinkedList;

import exception.SyntaxException;
import token.Assign;
import token.BinaryOperator;
import token.Identifiers;
import token.KeyWords;
import token.Operators;
import token.Separators;
import token.Token;
import token.Values;
import type.AssignType;
import type.KeywordType;
import type.OperatorsType;
import type.SeparatorsType;
import type.TokenType;
import type.ValuesType;

public class Analysis {

	private Input input;
	
	private LinkedList<Token> tokens;
	private Token preToken;
	
	public Analysis(Input temp) {
		input = temp;
		tokens = new LinkedList<Token>();
		
	}
	public void Lexical() throws SyntaxException{
	
		
		while(!input.isEnd()) {	
			while(isBlank(input.readCh())&&!input.isEnd())
				input.next();
			// separator
			//System.out.println((char)input.readCh());
			if(input.readCh()=='\n'||input.readCh()=='\r'||input.readCh()==10){
				
				if(tokens.getLast().getToken()==TokenType.SEPARATORS) {
					Separators temp = (Separators) tokens.getLast();
					if(temp.getSep()!=SeparatorsType.SEMICOLON&&(temp.getSep()!=SeparatorsType.LEFTBRACE)&&(temp.getSep()!=SeparatorsType.RIGHTBRACE))
						throw new SyntaxException(input.getLine(),input.getPosition(),"End without Semicolon!");
					else if(temp.getSep()==SeparatorsType.RIGHTBRACE&&(tokens.get(tokens.size()-2).getToken()!=TokenType.SEPARATORS)){
						throw new SyntaxException(tokens.get(tokens.size()-2).getline(),tokens.get(tokens.size()-2).getPos()+1,"End without valid Separator!");
					}else {
						//System.out.println(input.readCh());
						input.next();
						if(input.isEnd())
							break;
						if(input.readCh()=='\n'||input.readCh()=='\r'||input.readCh()==10)
							input.plusLine();
							continue;
						//System.out.println(input.readCh());
						
					}
				}
				else
					throw new SyntaxException(input.getLine(),input.getPosition(),"End without Semicolon!");
					
					
			}
				
			
			
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
			//System.out.println(input.readCh());
			//keyword
			KeyWords temp5=findKeyword();
			if(temp5!=null){
				tokens.add(temp5);
				continue;
			}
			//System.out.println(input.readCh());
			//identifier
			Identifiers tempI=findIdentifier();
			if(tempI!=null){
				tokens.add(tempI);
				continue;
			}
			//System.out.println(input.readCh());
				throw new SyntaxException(input.getLine(),input.getPosition(),"invalid token!");
		}
			
		if(input.isEnd()){
			Separators tempS = findSeparators();
			if(tempS!=null&&(tempS.getSep()==SeparatorsType.SEMICOLON||tempS.getSep()==SeparatorsType.RIGHTBRACE)){
				//System.out.println((char)input.readCh());
					tokens.add(tempS);
					if(tempS.getSep()==SeparatorsType.RIGHTBRACE&&tokens.get(tokens.size()-2).getToken()!=TokenType.SEPARATORS){
						throw new SyntaxException(tokens.get(tokens.size()-2).getline(),tokens.get(tokens.size()-2).getPos()+1,"End without valid Separator!");

					}
					return;		
			}
			else
			throw new SyntaxException(input.getLine(),input.getPosition(),"End without Semicolon!");
		}
	
		
		
		
	}
	
	public boolean isBlank(int ch) {
		if(ch == -1 || ch == '\t' || ch == ' ')
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
		case ',':
			temp = new Separators(SeparatorsType.COMMA);
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
	public Assign findAssign() throws SyntaxException {
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
	
	public Operators findOperators() throws SyntaxException {
		Operators temp;
		temp = new Operators();
		switch(input.readCh()){
		case '+':
			if(!isNotCompound(temp,'=','+',OperatorsType.ADD,OperatorsType.DUAL_ADD)) 		
				return null;
			break;
		case '-':
			if(!isNotCompound(temp,'=','-',OperatorsType.SUBTRACT,OperatorsType.DUAL_SUBTRACT)) 		
				return null;
			break;
		case '*':
			if(!isNotCompound(temp,'=',OperatorsType.MULTIPLY)) 		
				return null;		
			break;
		case '/':
			temp = new BinaryOperator(OperatorsType.DIVISION);
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
	
	public void isCompound(Operators temp,OperatorsType otype1,OperatorsType otype2) throws SyntaxException {
		input.next();
		int count=0;
		while(isBlank(input.readCh())&&!input.isEnd()) {
			input.next();
			count++;
		}
		if(input.readCh() == '=') {
			if(count>=1)
				throw new SyntaxException(input.getLine(),input.getPosition()-1,"运算符中间有空白");
			temp.setOperatorsType(otype2);
		}
		else {
			temp.setOperatorsType(otype1);
			input.previous();
			while(count>0) {
				input.previous();
				count--;
			
			}
		}
	}
	public boolean isCompound(Operators temp,char aim,OperatorsType otype) throws SyntaxException {
		input.next();
		int count=0;
		while(isBlank(input.readCh())&&!input.isEnd()) {
			input.next();
			count++;
		}
		if(input.readCh() == aim) {
			if(count>=1)
				throw new SyntaxException(input.getLine(),input.getPosition()-1,"运算符中间有空白");
			temp.setOperatorsType(otype);
			return true;
		}
		input.previous();
		while(count>0) {
			input.previous();
			count--;
		
		}
		return false;
	}
	public boolean isCompound(Assign temp,char aim,AssignType atype) throws SyntaxException {
		input.next();
		int count=0;
		while(isBlank(input.readCh())&&!input.isEnd()) {
			input.next();
			count++;
		}
		if(input.readCh() == aim) {
			if(count>=1)
				throw new SyntaxException(input.getLine(),input.getPosition()-1,"运算符中间有空白");
			temp.setAssignType(atype);
			return true;
		}
		input.previous();
		while(count>0) {
			input.previous();
			count--;
		
		}
		return false;
	}
	public boolean isNotCompound(Operators temp,char aim,OperatorsType otype){
		
		input.next();
		if(input.readCh() != aim) {
			temp.setOperatorsType(otype);
			input.previous();
			return true;
		}
		input.previous();
		return false;
	}
	
	public boolean isNotCompound(Operators temp,char aim,char aim2,OperatorsType otype,OperatorsType otype2) throws SyntaxException{
		input.next();
		int count=0;
		while(isBlank(input.readCh())&&!input.isEnd()) {
			input.next();
			count++;
		}
		if(input.readCh()==aim2){
			temp.setOperatorsType(otype2);
			if(count>=1)
				throw new SyntaxException(input.getLine(),input.getPosition()-1,"运算符中间有空白");
			return true;
		}
		if(input.readCh() != aim) {
			temp.setOperatorsType(otype);
				while(count>0) {
					input.previous();
					count--;
				
				}
			input.previous();
			return true;
		}	
		while(count>0) {
			input.previous();
			count--;
		
		}
		input.previous();
		return false;
	}
	
	public Values findValues() throws SyntaxException {
		Values temp;
		int intVal;
		int num=0;
		if(Character.isDigit(input.readCh())) {
			temp = new Values(ValuesType.INTEGER);
			intVal = 0;
			
			while(!input.isEnd()&&(Character.isDigit(input.readCh())||input.readCh()=='.')) {			
				if(input.readCh()=='.')
					break;
				intVal += input.readCh()-48;
				intVal *= 10;
				if(intVal<0)
					throw new ArithmeticException("Integer Overflow!");
				
				input.next();
				num++;
			}
			if(input.readCh()=='_'||Character.isLetter(input.readCh())){
				for(int j=0;j<num;j++){
					input.previous();
				}
				throw new SyntaxException(input.getLine(),input.getPosition(),"invalid token!");
			}		
			intVal = intVal/10;
			if(input.readCh()=='.')
				return processDouble(intVal,temp);	
			if(midBlank())
				throw new SyntaxException(input.getLine(),input.getPosition()-1,"数字中间有空白");		
			temp.setIntValue(intVal);
			temp.setLine(input.getLine());
			temp.setPos(input.getPosition()-1);
			return temp;
			
		}
		
		if(input.readCh()=='\'') {
			char charVal = 0;	
			input.next();
			charVal = (char) input.readCh();
			input.next();
			if(!input.isEnd()&&input.readCh()=='\'') {
			temp = new Values(ValuesType.CHAR);
			temp.setLine(input.getLine());
			temp.setPos(input.getPosition()-1);
			temp.setChar(charVal);
			input.next();
			return temp;
			}
			else {
				input.previous();
				input.previous();
				temp = null;
				return null;
				
			}
			}
			
		int count = 0;  
		if(input.readCh()=='\"') {
			String strval = "";
			while(!input.isEnd()) {
				count ++;
				input.next();
				if(input.readCh()=='\"')
					break;
				strval+=(char)input.readCh();
			}
			if(input.readCh()=='\"') {
			temp = new Values(ValuesType.STRING);
			temp.setLine(input.getLine());
			temp.setPos(input.getPosition()-1);
			temp.setStr(strval);
			input.next();
			return temp;
			}
			else {
				while(count>=0) {
					count--;
					input.previous();
				}
				temp = null;
				return temp;
			}
		}
		
		temp = null;
		return null;
		
	}
	
	public Values processDouble(int intVal, Values temp2) throws SyntaxException {
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
		if(midBlank())
			throw new SyntaxException(input.getLine(),input.getPosition()-1,"数字中间有空白");	
		while(count>=0) {
		tail = tail/10;
		count --;
		}
		doub = intVal+tail;
		temp2.setType(ValuesType.DOUBLE);
		temp2.setLine(input.getLine());
		temp2.setPos(input.getPosition()-1);
		temp2.setDouble(doub);
		return temp2;	
	}
	
	public boolean midBlank() {
		if(!input.isEnd()) {
		while(isBlank(input.readCh()))
			input.next();
		if(isIdAlphabet(input.readCh())||input.readCh()=='.')
			return true;
		}
		return false;
	}
	public boolean isDigit(int ch){
		return Character.isDigit(ch);
	}

	public boolean isLetter(int ch) {
		return Character.isLetter(ch);
	}

	public boolean isLetter() {
		return isLetter(input.readCh());
	}

	public boolean isIdAlphabet(int ch) {
		return isDigit(ch) || isLetter(ch) || ch == '_';
	}

	public boolean isIdAlphabet() {
		return isIdAlphabet(input.readCh());
	}

	public boolean isKeyword(String keyword){
		int len=keyword.length();
		int []chs=new int[len+1];
		if(input.getLength()-input.getI()<len)
			return false;
		for(int i=0;i<len;i++){
			chs[i]=input.readCh();
			if(chs[i]==(int)';'|chs[i]==(int)'\n'|chs[i]==(int)'{'|chs[i]==(int)'}'){
				for(int j=0;j<i;j++){
					input.previous();
				}
				return false;
			}
			if(!input.isEnd())
				input.next();
		}
		if(input.getLength()-input.getI()==len)
			chs[len]=-1;
		else
			chs[len]=input.readCh();

		for(int i=0;i<len;i++){
			if((int)keyword.charAt(i)!=chs[i]){
				for(int j=0;j<len;j++){
					input.previous();
				}
				return false;
			}
		}
		if(isIdAlphabet(chs[len])){
			for(int i=0;i<len;i++){
				input.previous();
			}
			return false;
		}


		return true;

	}

	public KeyWords findKeyword(){
		KeyWords keyword;
		if (isKeyword("if"))
			keyword = new KeyWords(KeywordType.IF);
		else if (isKeyword("else"))
			keyword = new KeyWords(KeywordType.ELSE);
		else if (isKeyword("while"))
			keyword = new KeyWords(KeywordType.WHILE);
		else if (isKeyword("for"))
			keyword = new KeyWords(KeywordType.FOR);
		else if (isKeyword("int"))
			keyword = new KeyWords(KeywordType.INT);
		else if (isKeyword("double"))
			keyword = new KeyWords(KeywordType.DOUBLE);
		else if (isKeyword("string"))
			keyword = new KeyWords(KeywordType.STRING);
		else if (isKeyword("bool"))
			keyword = new KeyWords(KeywordType.BOOL);
		else if (isKeyword("char"))
			keyword = new KeyWords(KeywordType.CHAR);
		else if (isKeyword("true"))
			keyword = new KeyWords(KeywordType.TRUE);
		else if (isKeyword("false"))
			keyword = new KeyWords(KeywordType.FALSE);
		else if (isKeyword("print"))
			keyword = new KeyWords(KeywordType.PRINT);
		else
			return null;
		input.previous();
		keyword.setLine(input.getLine());
		keyword.setPos(input.getPosition());
		input.next();

		return keyword;

	}

	public Identifiers findIdentifier() throws SyntaxException {
		Identifiers identifier=isIdentifier();
		if(identifier==null){
			return null;
		}else{
			identifier.setLine(input.getLine());
			identifier.setPos(input.getPosition()-1);
			if(midBlank())
				throw new SyntaxException(input.getLine(),input.getPosition()-1,"标识符中间有空白");
			return identifier;
		}
	}

	public Identifiers isIdentifier() throws SyntaxException {
		if (input.readCh() != '_' && !isLetter())
			return null;

		StringBuffer bString = new StringBuffer();
		while (isIdAlphabet()) {
			bString.append((char) input.readCh());
			if(input.isEnd())
				break;
			input.next();
		}
		//input.previous();
				
		Identifiers identifier = new Identifiers();
		identifier.setId(bString.toString());
		return identifier;

	}
	
	public void print() {
	    LinkedList<Token> temp=new LinkedList(tokens);
		
		while(!temp.isEmpty())
		System.out.println(temp.pollFirst().display());
	}
	
	public Token getToken() {
	        return tokens.peekFirst();
	    }
	public Token getPreToken() {
		return this.preToken;
	}

	public Token getNextToken(){
	        if(tokens.size() < 2)
	            return null;
	        return tokens.get(1);
	    }

	public boolean isendof() {
	        return tokens.isEmpty();
	    }

	public void next() {
		if(!isendof())
	        preToken=tokens.pollFirst();
		else
			System.out.println("token序列已为空");
		
        }	
	public LinkedList<Token> getList(){
		return this.tokens;
	}
		
    
}

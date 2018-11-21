package main;

import java.util.LinkedList;
import java.util.Stack;

import exception.SyntaxException;
import token.BinaryOperator;
import token.ExpressionToken;
import token.Identifiers;
import token.KeyWords;
import token.Operators;
import token.Separators;
import token.Token;
import token.UnaryOperator;
import token.Values;
import type.KeywordType;
import type.OperatorsType;
import type.SeparatorsType;
import type.TokenType;
import type.ValuesType;


public class Parser {
	private Analysis analysis;
	
	private LinkedList<Token> tokens;
	private Stack<Stack<ExpressionToken>> calStack;
	private int line;
	private int pos;
	private String abc="";
	
	
	public Parser(Analysis temp) {
		analysis = temp;
		tokens = new LinkedList<Token>();
		calStack=new Stack<Stack<ExpressionToken>>();
		
		
	}
	
	 //check whether current token is operator
	 private boolean isOperator() {
	        if (analysis.getToken() == null)
	            return false;
	        return analysis.getToken().getToken() == TokenType.OPERATORS;
	    }
	 //check whether current token is certain operator
	 private boolean isOperator(OperatorsType type){
	        if(!isOperator())
	            return false;
	        Operators temp=(Operators) analysis.getToken();
	        return temp.getOp()==type;
	    } 
	 //check whether current token is certain operator. if so, remove it
	 private boolean detectOperator(OperatorsType type) {
	        if(!isOperator(type))
	            return false;
	        analysis.next();
	        return true;
	    }
	 // check whether current token is separator
	 private boolean isSeparator() {
	        if (analysis.getToken() == null)
	            return false;
	        return analysis.getToken().getToken() == TokenType.SEPARATORS;
	    }
	 // check whether current token is certain separator
	 private boolean isSeparator(SeparatorsType type){
	        if(!isSeparator())
	            return false;
	        Separators temp = (Separators)analysis.getToken();
	        return temp.getSep()==type;
	    }
	 //check whether current token is certain operator. if so, remove it
	 private boolean detectSeparator(SeparatorsType type) {
	        if(!isSeparator(type))
	            return false;
	        return true;
	    }
	 // check whether current token is a keyword
	 private boolean isKeyword(){
	        if(analysis.getToken() == null)
	            return false;
	        return analysis.getToken().getToken() == TokenType.KEYWORDS;
	 }
	 // check whether current token is a given type keyword
	 private boolean isKeyword(KeywordType type){
	        if(!isKeyword())
	            return false;
	        KeyWords temp = (KeyWords)analysis.getToken();
	        return temp.getKeyVal()==type;
	    }
	 // check whether current token is a given type keyword. if so, remove it
	 private boolean detectKeyword(KeywordType type){
	        if(!isKeyword(type))
	            return false;
	        analysis.next();
	        return true;
	    }
	 // check whether current token is a identifier
	 private String detectIdentifier() {
	        if (analysis.getToken() == null)
	            return null;
	        if (analysis.getToken().getToken() != TokenType.IDENTIFIERS)
	            return null;
	        
            Identifiers temp = (Identifiers)analysis.getToken();
	        return temp.getId();
	    }
	 // check whether current token is a valuetype
	 private ValuesType detectValue() {
		 if(analysis.getToken()==null)
			 return null;
		 else if(analysis.getToken().getToken()!=TokenType.VALUES)
			 return null;
		 Values temp = (Values)analysis.getToken();
		 return temp.getType();
	 }
	 private boolean isUnaryOperator(Operators temp) {
		 if(temp.getOp()==OperatorsType.NOT||temp.getOp()==OperatorsType.NEGATIVESIGN)
			 return true;
		 return false;
		 
	 }
	 private UnaryOperator unaryOperation(UnaryOperator temp) throws SyntaxException {
		 if(temp.getOp()==OperatorsType.NOT) {
			 analysis.next();
			if( detectValue()==ValuesType.BOOLEAN) {
				temp.setChild(analysis.getToken());
				return temp;
			}
			else 
				throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "Redundant Operator");
				
		 }
		 if(temp.getOp()==OperatorsType.SUBTRACT) {
			 analysis.next();
			 if(detectValue()==ValuesType.DOUBLE||detectValue()==ValuesType.INTEGER) {
				 temp.setChild(analysis.getToken());
				 temp.setOperatorsType(OperatorsType.NEGATIVESIGN);
				 return temp;
				 
			 }
			 else
				 throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "Redundant Operator");
				 
				 
		 }
		 	throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "Unknown UnaryOperator");
		
	 }
	 
	// check whether current token belongs to expression
	 private boolean isExpressionToken() {
	        if (analysis.getToken() == null)
	            return false;
	        if (analysis.getToken() instanceof ExpressionToken)
	            return true;
	        if (analysis.getToken() instanceof Separators){
	            Separators temp= (Separators)analysis.getToken();
	            return temp.getSep()== SeparatorsType.LEFTPARENTHESES
	                    || temp.getSep() == SeparatorsType.RIGHTPARENTHESES;
	        }
	        return false;
	    }
	 private Stack<ExpressionToken> detectExpression() throws SyntaxException {
		 Stack<ExpressionToken> operandStack= new Stack<ExpressionToken>();//后缀表达式栈
		    Stack<Operators> operatorStack=new Stack<Operators>();//操作符栈
		   
	        while (isExpressionToken()) {
	        	
	            // Whenever meets "(" go recursion
	            if (detectSeparator(SeparatorsType.LEFTPARENTHESES)) {
	            	if(!operandStack.isEmpty())
	            		if(analysis.getPreToken().getToken()!=TokenType.OPERATORS)
	            			 throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "No Operators Before Left Parentheses");
	            	analysis.next();
	            	//
	            	System.out.println(analysis.getToken().display());
	                calStack.push(detectExpression());  
	                Values val = new Values(ValuesType.DOUBLE);
	                val.setToken(TokenType.EXPRESSION);
	                operandStack.push(val);
	                //
	                if (detectSeparator(SeparatorsType.RIGHTPARENTHESES)) {
	                	if(analysis.getNextToken().getToken()!=TokenType.SEPARATORS&&analysis.getNextToken().getToken()!=TokenType.OPERATORS)
	                		throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "No Operators After Right Parentheses");
	                	else	
	                		analysis.next();
	                	continue;
	                }
	                	else
	                    throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "unmatched left parentheses");
	            }
	            // Whenever meets ")" or "]" break recursion
	            else if (isSeparator(SeparatorsType.RIGHTPARENTHESES) || isSeparator(SeparatorsType.RIGHTBRACKET))
	                break;
	            // Operator
	            else if (isOperator()) {
	                Operators temp = (Operators)analysis.getToken();
	                if(operandStack.isEmpty()&&(temp.getOp()==OperatorsType.NOT||temp.getOp()==OperatorsType.SUBTRACT)) {
	                	UnaryOperator uop= new UnaryOperator(temp);
	                	operandStack.push(unaryOperation(uop));
	                	analysis.next();
	                	continue;
	                }//负号现在只有一种方法检测的出来，就是带括号然后(-1)这种 手动输入的负号 只有-加value 然后 带括号才算合法 否则词法都过不了，词法会检测operator链接问题
	                if (operatorStack.isEmpty()) {
	                    operatorStack.push(temp);
	                    analysis.next();
	                    continue;
	                }
	                if(analysis.getPreToken().getToken()==TokenType.OPERATORS) {
	                		if(temp.getOp()==OperatorsType.NOT||temp.getOp()==OperatorsType.SUBTRACT) {
	                	 UnaryOperator uop=new UnaryOperator(temp);
	                		operandStack.push(unaryOperation(uop));
	                		analysis.next();
	                		continue;
	                		}
	                		else
	                			throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "Redundant Operator");   		
	                }
	                	
	                Operators previous = operatorStack.peek();
	                if (Operators.isPrioriThan(temp, previous)) {
	                    operatorStack.push(temp);
	                    analysis.next();
	                    continue;
	                } 
	                else {
	                    while (!operatorStack.empty() && !Operators.isPrioriThan(temp, operatorStack.peek())) 
	                        operandStack.push(operatorStack.pop());
	                        
	                    }
	                    operatorStack.push(temp);
	                    analysis.next();  
	                    continue;
	            }
	            else if(detectIdentifier()!=null) {
	            	operandStack.add((ExpressionToken)analysis.getToken());
	            	analysis.next();
	            	continue;
	            }
	            else if(detectValue()!=null&&(detectValue()==ValuesType.BOOLEAN||detectValue()==ValuesType.DOUBLE||detectValue()==ValuesType.INTEGER)) {
	            	operandStack.add((ExpressionToken)analysis.getToken());
	            	analysis.next();
	            	continue;
	            
	            }
	            
	        }
	        while (!operatorStack.isEmpty()) {
	            operandStack.push(operatorStack.pop());
	        }
	        
	        return operandStack;
	 }
	 //在词法部分 就解决操作符多个问题 报不合法
	 private ExpressionToken calculate(Stack<ExpressionToken> temp) throws SyntaxException {
		
		 ExpressionToken token1;
		 ExpressionToken token2;
		 ExpressionToken token3;
		 boolean result1=true;
		 
		 Stack<ExpressionToken> factor = new Stack<ExpressionToken>();
		 Stack<ExpressionToken> stack =reverseStack(temp);
		 
		 
		
		 
		 while(!stack.isEmpty()) {
			 token1 = stack.pop();
			 if(token1.getToken()==TokenType.EXPRESSION) {
				 factor.push(calculate(calStack.pop()));
			 }
			 if(token1.getToken()==TokenType.VALUES||token1.getToken()==TokenType.IDENTIFIERS) {	
				 System.out.println(token1.display());
				 factor.push(token1);
				 continue;
			 }
			 else if(token1.getToken()==TokenType.OPERATORS) {
				 Operators op = (Operators)token1;
				 if(isUnaryOperator(op)) {
					 UnaryOperator uop =(UnaryOperator)op;
					 Values va=(Values)uop.getChild();
					 if(va.getType()==ValuesType.DOUBLE)
						 va.setDouble(-(va.getDouble()));
					 factor.push(va);
					 if(va.getType()==ValuesType.INTEGER)
						 va.setIntValue(-(va.getIntVal()));
					 continue;
				 }
				 if(factor.size()<2)
					 throw new SyntaxException( "Find Redundant Operator While Calculating ");   		
				 token3 =factor.pop();
				 token2 =factor.pop();
				 double a = 0;
				 double b = 0;
				 int c = 0;
				 int d = 0;
				 boolean e = false;
				 boolean f = false;
				 
				 if(token2.getToken()==TokenType.VALUES&&token3.getToken()==TokenType.VALUES) {
					 Values temp1=(Values)token2;
					 Values temp2=(Values)token3;
					 if(temp1.getType()==ValuesType.DOUBLE) {
						 e=true;
						 a = temp1.getDouble();	 
					 }	
					 else if(temp1.getType()==ValuesType.INTEGER) {
						 
						 c = temp1.getIntVal();
					 }
					 if(temp2.getType()==ValuesType.DOUBLE) {
						 f=true;
						
						 b = temp2.getDouble();	 
					 }	
					 else if(temp2.getType()==ValuesType.INTEGER) 
						 d = temp2.getIntVal();
				 
				
				 switch(op.getOp()) {
				 case ADD:
						 if(!opCalculate1(OperatorsType.ADD,temp1,a,b,c,d,e,f,factor))
							 throw new SyntaxException( "Error Operation While Calculating ");
							 
					 break;
				 case SUBTRACT:
					 if(!opCalculate1(OperatorsType.SUBTRACT,temp1,a,b,c,d,e,f,factor))
						 throw new SyntaxException( "Error Operation While Calculating ");
					 
					 break;
				 case MULTIPLY:
					 if(!opCalculate1(OperatorsType.MULTIPLY,temp1,a,b,c,d,e,f,factor))
						 throw new SyntaxException( "Error Operation While Calculating ");
					 break;
				 case DIVISION:
					 if(!opCalculate1(OperatorsType.DIVISION,temp1,a,b,c,d,e,f,factor))
						 throw new SyntaxException( "Error Operation While Calculating ");
					
					 break;
				 case MOD:
					 if(!opCalculate1(OperatorsType.MOD,temp1,a,b,c,d,e,f,factor))
						 throw new SyntaxException( "Error Operation While Calculating ");
				
					 break;
					 
//				 case AND:
//					 
//					 break;
//				 case EQUAL:
//					 
//					 break;
//				 case UNEQUAL:
//					
//					 break;
//				 case LESSTHAN:
//					
//					 break;
//					 
//				 case MORETHAN:
//					
//					 break;
//				 case LESSTHANOREQUAL:
//				
//					 break;
//				 case MORETHANOREQUAL:
//					
//					 break;
				 default:
					 throw new SyntaxException( "Error Operation While Calculating ");
					 
			
				 }
				
							 
			 }
			 } 	 
		 }
		 return factor.pop();
			 	 
	 }
	 
	 private boolean opCalculate1(OperatorsType type,Values temp1,double a, double b,int c,int d,boolean e, boolean f,Stack<ExpressionToken> factor) {
		 if(e&&f) {
				temp1.setType(ValuesType.DOUBLE);
				factor.push(opCalculate2(type,temp1,a,b));
				 return true;
			}
			else if(!e&&!f) {
				temp1.setType(ValuesType.INTEGER);
				factor.push(opCalculate2(type,temp1,c,d));
				 return true;
			}
			else if(!e&&f) {
				temp1.setType(ValuesType.DOUBLE);
				factor.push(opCalculate2(type,temp1,c,b));
				 return true;
				
			}
			else if(e&&!f) {
				temp1.setType(ValuesType.DOUBLE);
				factor.push(opCalculate2(type,temp1,a,d));
				 return true;
				
				
			}
		 return false;
		 
	 }
	 private Values opCalculate2(OperatorsType type,Values temp1,double a,double b) {
		 if(type==OperatorsType.ADD)
				temp1.setDouble(a+b);
			if(type==OperatorsType.SUBTRACT)
				temp1.setDouble(a-b);
			if(type==OperatorsType.DIVISION)
				temp1.setDouble(a/b);
			if(type==OperatorsType.MULTIPLY)
				temp1.setDouble(a*b);
			if(type==OperatorsType.MOD)
				temp1.setDouble(a%b);
		 return temp1;
	 }
	 private Values opCalculate2(OperatorsType type,Values temp1,int a,int b) {
		 if(type==OperatorsType.ADD)
				temp1.setIntValue(a+b);
			if(type==OperatorsType.SUBTRACT)
				temp1.setIntValue(a-b);
			if(type==OperatorsType.DIVISION)
				temp1.setIntValue(a/b);
			if(type==OperatorsType.MULTIPLY)
				temp1.setIntValue(a*b);
			if(type==OperatorsType.MOD)
				temp1.setIntValue(a%b);
		 return temp1;
	 }
	 private Stack<ExpressionToken> reverseStack(Stack<ExpressionToken> stack) {
		Stack<ExpressionToken> temp= new Stack<ExpressionToken>();
		while(!stack.isEmpty())
			temp.push(stack.pop());
		stack =temp;
		return stack;
	 }
	 public String printStack() throws SyntaxException {
		 while(!analysis.getList().isEmpty()) {
			 if(detectSeparator(SeparatorsType.SEMICOLON))
				 analysis.next();
			 if(analysis.getList().size()<=1)
				 break;
		 calStack.push(detectExpression());
		 if(calStack.isEmpty())
			 System.out.println("表达式为空");
		 
		Values temp =(Values) calculate(calStack.pop());
		return temp.display();
		
			 
		 }	 
		 return "Null";
		 
		 
	 }
}

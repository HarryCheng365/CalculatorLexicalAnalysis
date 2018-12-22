package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import statement.Return;
import statement.AssignExpression;
import statement.Expression;
import statement.For;
import statement.While;
import statement.IfElse;
import statement.Statement;
import exception.CMMException;
import exception.SyntaxException;
import statement.Initialization;
import token.Assign;
import token.BinaryOperator;
import token.ExpressionToken;
import token.Identifiers;
import token.KeyWords;
import token.Operators;
import token.Separators;
import token.Token;
import token.UnaryOperator;
import token.Values;
import token.Casting;
import type.AssignType;
import type.KeywordType;
import type.OperatorsType;
import type.SeparatorsType;
import type.TokenType;
import type.ValuesType;


public class Parser {
	private Analysis analysis;
	
	private LinkedList<Token> tokens;
	private HashMap<Integer,Stack<ExpressionToken>> calHash;
	private Stack<SeparatorsType> sepStack;
	private int line;
	private int pos;
	private int hashKey;
	private String abc="";
	
	
	public Parser(Analysis temp) {
		analysis = temp;
		tokens = new LinkedList<Token>();
		calHash = new HashMap<Integer,Stack<ExpressionToken>>();
		hashKey = 0;
		sepStack = new Stack<SeparatorsType>();	
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
	 private boolean isAssign() {
		 if(analysis.getToken()==null)
			 return false;
		 return analysis.getToken().getToken()==TokenType.ASSIGN;
		 
	 }
	 private boolean isAssign(AssignType type) {
		 if(!isAssign())
			 return false;
		 Assign temp =(Assign)analysis.getToken();
		 return temp.getAssignType()==type;
		 
	 }
	 private boolean detectAssign(AssignType type) {
		 if(!isAssign(type))
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
	 private ValuesType detectDataType() {
	        if (analysis.getToken() == null)
	            return null;
	        if (analysis.getToken().getToken() != TokenType.KEYWORDS)
	            return null;
	        KeyWords keyword = (KeyWords)analysis.getToken();
	        ValuesType result = Casting.KeywordTypeToValueType(keyword.getKeyVal());
	        if (result == null)
	            return null;
	        return result;
	    }

	 private boolean isUnaryOperator(Operators temp) {
		 if(temp.getOp()==OperatorsType.NOT||temp.getOp()==OperatorsType.NEGATIVESIGN||temp.getOp()==OperatorsType.POSITIVEASIGN)
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
		 if(temp.getOp()==OperatorsType.SUBTRACT||temp.getOp()==OperatorsType.ADD) {
			 if(temp.getOp()==OperatorsType.SUBTRACT)
				temp.setOperatorsType(OperatorsType.NEGATIVESIGN);
			 else if(temp.getOp()==OperatorsType.ADD)
				 temp.setOperatorsType(OperatorsType.POSITIVEASIGN);
			 analysis.next();
			 if(detectValue()==ValuesType.DOUBLE||detectValue()==ValuesType.INTEGER) {
				 temp.setChild(analysis.getToken());
			
				 
				 return temp;
				//-(-(-(-(-1*(3-7)*(-4)+2))+3)+4*12+3*(-(-(-(+3))))) 
			 }
			 else if(detectSeparator(SeparatorsType.LEFTPARENTHESES)) {
				  sepStack.push(SeparatorsType.LEFTPARENTHESES);
            	  analysis.next();	  
				  Values val = new Values(ValuesType.DOUBLE);
				  val.setInteger(new Integer(++hashKey));
				  calHash.put(val.getInteger(),detectExpression());
	              val.setToken(TokenType.EXPRESSION);
				  temp.setChild(val);
				  
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
	            	sepStack.push(SeparatorsType.LEFTPARENTHESES);
	            	 analysis.next();
	            	//
	    
	                Values val = new Values(ValuesType.DOUBLE);
	                val.setInteger(new Integer(++hashKey));
	                calHash.put(val.getInteger(), detectExpression());
	                val.setToken(TokenType.EXPRESSION);
	                operandStack.push(val);
	                //
	                if (detectSeparator(SeparatorsType.RIGHTPARENTHESES)) {
	                	if(analysis.getNextToken().getToken()!=TokenType.SEPARATORS&&analysis.getNextToken().getToken()!=TokenType.OPERATORS)
	                		throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "No Operators After Right Parentheses");
	                	else {
	                		analysis.next();
	                		continue;
	                		
	                	}
	                	
	                }
	                	else
	                    throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "Unmatched left parentheses");
	            }
	            // Whenever meets ")" or "]" break recursion
	            else if (isSeparator(SeparatorsType.RIGHTPARENTHESES) || isSeparator(SeparatorsType.RIGHTBRACKET)) {
	            	if(sepStack.isEmpty()||(sepStack.pop()!=SeparatorsType.LEFTPARENTHESES))
	            		throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "Unmatched Right parentheses");   		
	            	break;
	            }
	            // Operator
	            else if (isOperator()) {
	                Operators temp = (Operators)analysis.getToken();
	                if(operandStack.isEmpty()&&(temp.getOp()==OperatorsType.NOT||temp.getOp()==OperatorsType.SUBTRACT||temp.getOp()==OperatorsType.ADD)) {
	                	UnaryOperator uop= new UnaryOperator(temp);
	                	operandStack.push(unaryOperation(uop));
	                	analysis.next();
	                	continue;
	                }
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
				 
				 factor.push(calculate(calHash.get(((Values)token1).getInteger())));
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
					 if(uop.getChild().getToken()==TokenType.EXPRESSION) {
						 token1=calculate(calHash.get(((Values)uop.getChild()).getInteger()));
						 if(token1.getToken()==TokenType.VALUES) {
							 Values vb = (Values)token1;
							 factor.push(unaryConvert(vb,uop.getOp()));
						 continue;
						 }
						 else
							 throw new SyntaxException(analysis.getToken().getline(), analysis.getToken().getPos(), "括号前负号识别错误");   		
						 
					 }
					 Values va=(Values)uop.getChild();
					 factor.push(unaryConvert(va,uop.getOp()));
					 
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
				if(d>c&&type==OperatorsType.DIVISION) {
					temp1.setType(ValuesType.DOUBLE);
					factor.push(opCalculate2(type,temp1,(double)c,(double)d));			
				}
				else {
				temp1.setType(ValuesType.INTEGER);
				factor.push(opCalculate2(type,temp1,c,d)); 
				}
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
	 private Values unaryConvert(Values value, OperatorsType type) throws SyntaxException {
		 if(type == OperatorsType.NEGATIVESIGN) {
		 if(value.getType()==ValuesType.DOUBLE)
			 value.setDouble(-(value.getDouble()));
		
		 if(value.getType()==ValuesType.INTEGER)
			 value.setIntValue(-(value.getIntVal()));
		 return value;
		 }
		 else if(type == OperatorsType.POSITIVEASIGN) {
			 if(value.getType()==ValuesType.DOUBLE)
				 value.setDouble((value.getDouble()));
			
			 if(value.getType()==ValuesType.INTEGER)
				 value.setIntValue((value.getIntVal()));
			return value;
		 }
		 else 
			 throw new SyntaxException( "Error UnaryOperation While Calculating ");
		 
	 }
	 private Stack<ExpressionToken> reverseStack(Stack<ExpressionToken> stack) {
		Stack<ExpressionToken> temp= new Stack<ExpressionToken>();
		while(!stack.isEmpty())
			temp.push(stack.pop());
		stack =temp;
		return stack;
	 }
	 private Values detectExpression(Values val) throws SyntaxException {
		 val.setInteger(new Integer(++hashKey));
		 Stack<ExpressionToken> temp = detectExpression();
		 if(temp.isEmpty())
			 return null;
         calHash.put(val.getInteger(), temp);
         val.setToken(TokenType.EXPRESSION);
         val.setHashMap(calHash);
         calHash = new HashMap<Integer,Stack<ExpressionToken>>();
         hashKey=0;
         return val;
	 }
	 public String printStack() throws SyntaxException {
		 while(!analysis.getList().isEmpty()) {
			 if(detectSeparator(SeparatorsType.SEMICOLON))
				 analysis.next();
			 if(analysis.getList().size()<=1)
				 break;
			 Values val = new Values(ValuesType.DOUBLE);
			 val.setInteger(new Integer(++hashKey));
	         calHash.put(val.getInteger(), detectExpression());
	         val.setToken(TokenType.EXPRESSION);
		 if(calHash.isEmpty())
			 System.out.println("表达式为空");
		 
		Values temp =(Values) calculate(calHash.get(val.getInteger()));
		System.out.println(-(-(-(-(-1*(3-7)*(-4)+2))+3)+4*12+3*(-(-(-(+3))))));
		return temp.display();
		
			 
		 }	 
		 return "Null";
		 
		 
	 }
	 private AssignExpression detectAssignExpression() throws CMMException {
		 AssignExpression assign = new AssignExpression();
		 String id =detectIdentifier();
		 if (id == null)
	           throw new CMMException("missing identifier");
	     assign.setId(id);
	     analysis.next();
	     if (detectSeparator(SeparatorsType.SEMICOLON))
	           throw new CMMException("Assign Operation with no Expression !");
	     else if (isAssign()) {
	    	    assign.setAssign((Assign)analysis.getToken());
	    	    Values val = new Values(ValuesType.DOUBLE);
	            assign.setExpressionToken(detectExpression(val));
	     }
	     return assign;
		 
	 }
	 private Initialization detectInitialization() throws CMMException {
		 Initialization initialization = new Initialization();
		 ValuesType dataType = detectDataType();
	     if (dataType == null)
	        return null;
	     initialization.setValuesType(dataType);
	     analysis.next();
	     String id = detectIdentifier();
	     if (id == null)
	           throw new CMMException("missing identifier");
	     initialization.setId(id);
	     analysis.next();
	     if (detectSeparator(SeparatorsType.SEMICOLON))
	           initialization.setExpressionToken((new Values(dataType)));
	     else if (detectAssign(AssignType.ASSIGN)) {
	    	    Values val = new Values(ValuesType.DOUBLE);
	            initialization.setExpressionToken(detectExpression(val));
	     }
//	        else if (detectSeparator(SeparatorsType.LEFTBRACKET)){
//	            // array initialization statement
//	            initialization.setArray(true);
//	            ExpressionToken arrayLength = detectExpression();
//	            if(arrayLength == null){
//	                // deduce array length from initialization list
//	                if(!detectSeparator(SeparatorType.RIGHTBRACKET))
//	                    throwException("missing right bracket");
//	                if (!detectOperator(OperatorType.ASSIGN))
//	                    throwException("missing assign operator");
//	                if (!detectSeparator(SeparatorType.LEFTBRACE))
//	                    throwException("missing left brace");
//	                ExpressionToken element = detectExpression();
//	                if(element == null)
//	                    throwException("can not declare an array of length 0");
//	                initialization.addElement(element);
//	                int cnt = 1;
//	                while(detectSeparator(SeparatorType.COMMA)){
//	                    element = detectExpression();
//	                    if(element == null)
//	                        throwException("missing array element");
//	                    initialization.addElement(element);
//	                    cnt++;
//	                }
//	                if (!detectSeparator(SeparatorType.RIGHTBRACE))
//	                    throwException("missing right brace");
//	                Value len = new Value(ValueType.INTEGER);
//	                len.setIntValue(cnt);
//	                initialization.setArrayLength(len);
//	            }else{
//	                // array length is given
//	                initialization.setArrayLength(arrayLength);
//	                if(!detectSeparator(SeparatorType.RIGHTBRACKET))
//	                    throwException("missing right bracket");
//	                if(detectSeparator(SeparatorType.SEMICOLON))
//	                    return initialization;
//	                if (!detectOperator(OperatorType.ASSIGN))
//	                    throwException("missing assign operator");
//	                if (!detectSeparator(SeparatorType.LEFTBRACE))
//	                    throwException("missing left brace");
//	                ExpressionToken element = detectExpression();
//	                if(element == null)
//	                    throwException("can not declare an array of length 0");
//	                initialization.addElement(element);
//	                while(detectSeparator(SeparatorType.COMMA)){
//	                    element = detectExpression();
//	                    if(element == null)
//	                        throwException("missing array element");
//	                    initialization.addElement(element);
//	                }
//	                if (!detectSeparator(SeparatorType.RIGHTBRACE))
//	                    throwException("missing right brace");
//	            }
//	        }else
//	            throwException("missing semicolon");
	        return initialization;
		 
	 }
	 private IfElse detectIfBranch(IfElse ifElse) throws CMMException {
		 if(!detectKeyword(KeywordType.IF))
	            return null;
	        if(!detectSeparator(SeparatorsType.LEFTPARENTHESES)) 
	            throw new CMMException("missing left parentheses");
	        Values val= new Values(ValuesType.DOUBLE);
	        ExpressionToken condition = detectExpression(val);
	        if(condition == null)
	        	throw new CMMException("missing condition");
//	        if(!detectSeparator(SeparatorsType.RIGHTPARENTHESES))
//	        	throw new CMMException("missing right parentheses");
	        
	        LinkedList<Statement> ifbranch = detectCodeBlock();
	        ifElse.setIfBranch(condition, ifbranch);
	        return ifElse;
	 }
	 private IfElse detectIfElse() throws CMMException{
	        IfElse ifElse = new IfElse();
	        if(detectIfBranch(ifElse)==null)
	        	return null;
	        // exist else branch
	        while(detectKeyword(KeywordType.ELSE)){
	        	if(detectIfBranch(ifElse)==null) {
	        		LinkedList<Statement> elsebranch = detectCodeBlock();
	            	ifElse.setElseBranch(elsebranch);
	            	break;
	        	}
	        	
	        }

	        return ifElse;
	    }
	 
	 private While detectWhile() throws CMMException{
	        While wStat = new While();
	        if(!detectKeyword(KeywordType.WHILE))
	            return null;
	        if(!detectSeparator(SeparatorsType.LEFTPARENTHESES))
	            throw new CMMException("missing left parentheses");
	        analysis.next();
	        Values val= new Values(ValuesType.DOUBLE);
	        ExpressionToken condition = detectExpression(val);
	        if(condition == null)
	            throw new CMMException("missing condition");
	        if(!detectSeparator(SeparatorsType.RIGHTPARENTHESES))
	            throw new CMMException("missing right parentheses");
	        analysis.next();

	        LinkedList<Statement> loopStatement = detectCodeBlock();
	        wStat.setCondition(condition);
	        wStat.setLoopStatements(loopStatement);

	        return wStat;
	    }
	 private For detectFor() throws CMMException{
	        For fStatement = new For();
	        if(!detectKeyword(KeywordType.FOR))
	            return null;
	        if(!detectSeparator(SeparatorsType.LEFTPARENTHESES))
	            throw new CMMException("missing left parentheses");
	        analysis.next();
	        Values val= new Values(ValuesType.DOUBLE);
	        ExpressionToken init = detectExpression(val);
	        if(!detectSeparator(SeparatorsType.SEMICOLON))
	            throw new CMMException("missing semicolon");
	        analysis.next();
	        Values valb= new Values(ValuesType.DOUBLE);
	        ExpressionToken condition = detectExpression(valb);
	        if(!detectSeparator(SeparatorsType.SEMICOLON))
	            throw new CMMException("missing semicolon");
	        analysis.next();
	        Values valc= new Values(ValuesType.DOUBLE);
	        ExpressionToken incr = detectExpression(valc);
	        if(!detectSeparator(SeparatorsType.RIGHTPARENTHESES))
	            throw new CMMException("missing right parentheses");
	        analysis.next();

	        LinkedList<Statement> loopStatement = detectCodeBlock();
	        fStatement.setInit(init);
	        fStatement.setCondition(condition);
	        fStatement.setIncr(incr);
	        fStatement.setLoopStatements(loopStatement);

	        return fStatement;
	    }
	 
	 private Return detectReturn() throws CMMException{
	        Return rStatement = new Return();
	        if(!detectKeyword(KeywordType.RETURN))
	            return null;
	        Values val = new Values(ValuesType.DOUBLE);
	        ExpressionToken returnValue = detectExpression(val);
	        if(returnValue == null)
	            throw new CMMException("missing returned value");
	        rStatement.setReturnValue(returnValue);

	        return rStatement;
	    }
	 public Statement detectStatement() throws CMMException{
	        // ignore all empty statements
	        while(detectSeparator(SeparatorsType.SEMICOLON))
	        	analysis.next();

	        Initialization initialization = detectInitialization();
	        if(initialization != null)
	            return initialization;

	        IfElse ifElse = detectIfElse();
	        if(ifElse != null)
	            return ifElse;

	        While wStat = detectWhile();
	        if(wStat != null)
	            return wStat;

	        For fStatement = detectFor();
	        if(fStatement != null)
	            return fStatement;

	        Return rStatement = detectReturn();
	        if (rStatement != null)
	            return rStatement;

	        Expression expression = new Expression();
	        Values val = new Values(ValuesType.DOUBLE);
	        ExpressionToken root = detectExpression(val);
	        if(root != null){
	            expression.setExpressionToken(root);
	            return expression;
	        }
	        return null;
	    }
	 public LinkedList<Statement> detectStatements() throws CMMException{
	        // check statements
	        LinkedList<Statement> statements = new LinkedList<>();
	        Statement statement;
	        while((statement = detectStatement()) != null){
	            statements.add(statement);
	        }
	        return statements;
	    }
	 private LinkedList<Statement> detectCodeBlock() throws CMMException{
	        // check left-brace
	        if (!detectSeparator(SeparatorsType.LEFTBRACE))
	        	throw new CMMException("missing left brace");
	        analysis.next();

	        LinkedList<Statement> statements = detectStatements();

	        if (!detectSeparator(SeparatorsType.RIGHTBRACE))
	        	throw new CMMException("missing right brace");
	       analysis.next();

	        return statements;
	    }

}

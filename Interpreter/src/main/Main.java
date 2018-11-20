package main;

import exception.SyntaxException;

public class Main {
	public static void main(String[] args) throws SyntaxException{
		Input input = new Input("/Users/Haoyu/Documents/CalculatorLexicalAnalysis/Interpreter/src/main/source.txt");
	    Analysis analysis = new Analysis(input);
	    analysis.Lexical();
	    analysis.print();
	    Parser parser = new Parser(analysis);
	    parser.printStack();
	    //我要在对应位置 加入相应的算符 刚好是后缀表达式求值
	}
}

package main;

import exception.SyntaxException;

public class Main {
	public static void main(String[] args) throws SyntaxException{
		Input input = new Input("../Interpreter/src/main/source.txt");
	    Analysis analysis = new Analysis(input);
	    analysis.Lexical();
	    analysis.print();
		
		
	
		
	}
}

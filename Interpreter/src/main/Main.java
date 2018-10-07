package main;

public class Main {
	public static void main(String[] args) {
		Input input = new Input("/Users/Haoyu/eclipse-workspace/Interpreter/src/main/source.txt");
	    Analysis analysis = new Analysis(input);
	    analysis.Lexical();
	    analysis.print();
		
		
	
		
	}
}

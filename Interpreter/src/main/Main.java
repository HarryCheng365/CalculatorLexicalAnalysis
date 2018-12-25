package main;
import java.io.File;
import java.io.FileOutputStream;

import exception.CMMException;
import exception.SyntaxException;
import statement.Statement;

public class Main {
	public static String filepath="/Users/Haoyu/Documents/CalculatorLexicalAnalysis/Interpreter/src/main/source.txt";
	public static String Interpreter() throws SyntaxException{
		Input input = new Input("/Users/Haoyu/Documents/CalculatorLexicalAnalysis/Interpreter/src/main/source.txt");
	    Analysis analysis = new Analysis(input);
	    analysis.Lexical();
	    analysis.print();
	    Parser parser = new Parser(analysis);
	    return parser.printStack();
	    //我要在对应位置 加入相应的算符 刚好是后缀表达式求值
	}
	public static boolean writeTextFile(String content) {
		File file= new File(filepath);
		try {
			if(!file.exists())
				file.createNewFile();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		FileOutputStream fileop = null;
		try {
			fileop = new FileOutputStream(file);
			fileop.write(content.getBytes("utf-8"));
			fileop.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
//	public static void main(String[] args) throws CMMException {
//		Input input = new Input("/Users/Haoyu/Documents/CalculatorLexicalAnalysis/Interpreter/src/main/source.txt");
//	    Analysis analysis = new Analysis(input);
//	    analysis.Lexical();
//	    analysis.print();
//	    Parser parser = new Parser(analysis);
//	    System.out.println(Statement.listDisplay(parser.detectStatements()));
//	    
//		
//	}
	
}

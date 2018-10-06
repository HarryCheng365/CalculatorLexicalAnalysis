package main;

public class Main {
	public static void main(String[] args) {
		Input input = new Input("/Users/Haoyu/eclipse-workspace/Chomsky/src/main/source.txt");
		input.next();
		input.next();
		System.out.println(input.readCh());
		input.previous();
		System.out.println(input.readCh());
		input.previous();
		System.out.println(input.readCh());
		input.previous();
		System.out.println(input.readCh());
		
		
	
		
	}
}

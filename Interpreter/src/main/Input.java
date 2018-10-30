package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class Input {
	private List<Integer> list;
	private int line;
	private int position;
	private Integer ch;
	private int i;
	public Input(String inputFile) {
		try {
			Reader reader = new InputStreamReader(new FileInputStream(inputFile));
			list = new ArrayList<Integer>();
			i = 0;
			int head = reader.read();
			int tail = reader.read();
			while(head != -1) {
				list.add(head);
				head = tail;
				tail = reader.read();
			}
			reader.close();
			ch = list.get(0);
			line = 1;
			position = 0;
			
	}catch(IOException e) {
		System.err.println(e.getMessage());
		
	};
	
}
	
	public boolean isEnd() {
		return i == list.size()-1;
	}

	public boolean isFinished(){
		return i==list.size();
	}//此函数用来解决readCh()在结尾数字为整数时假越界的问题
	
	public int readCh() {
		if(isFinished()) {
			System.out.println("已到list最后一个元素");
			return -1;
		}
			
		return ch;
}
	public void next() {
		if(isEnd())
			return;
		i++;
		if(i==44){
			i=i;
		}
		ch = list.get(i);
		if(ch == '\n') {
			line += 1;
			position = -2;
		}
		position += 1;
	}
	
	
	public void previous() {
		if(i-1>=0){
			i--;
			//如果减去一行怎么办= =加一个存上一行position的操作？还是认为previous操作不会发生在换行时？（至今previous没有发生在换行时）
			//我觉得这个问题应该碰不到，因为我们强制一行必须分号+换行符结尾，不存在一个语句跨行的情况，所以应该碰不到。
			position--;
		}else{
			System.out.println("已到list第一个元素");
		}
		ch = list.get(i);
	}
	
	public int getLine() {
		return line;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void display() {
		for(Integer temp:list) {
			System.out.println(temp);
		}
	}
	public int getI(){
		return i;
	}

	public int getLength(){
		return list.size();
	}

}
// 这里使用迭代器会有多线程问题 虽然我没清楚到底问题在哪 但是单线程下的改写条件 对本题不是太适合
//由于我不想通过remove的方式来实现链表的next推进 想要保存前值 或者后值 所以只能牺牲空间开销和查询时间
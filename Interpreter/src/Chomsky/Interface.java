package Chomsky;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Container;

public class Interface {

	private JFrame frame;
	private static JLabel input;
	private static JLabel output;
	private static Map<String, ArrayList<String>> map;
	private static ArrayList<String> lList;
	private static ArrayList<String> tList;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con=frame.getContentPane();
		JButton btnNewButton = new JButton("Open");
		
		btnNewButton.setVisible(true);
		con.setLayout(new GridLayout(3,1));
		input=new JLabel();
		output=new JLabel("文法是");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc=new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(),"Choose");
				File file=jfc.getSelectedFile();
				if(file.isDirectory()) {
					System.out.println("wenjianjia"+file.getAbsolutePath());
					
				}else if(file.isFile()) {
					System.out.println(jfc.getSelectedFile().getName());
					readFile(file);
					
				}				
			
			}
			
		});
		con.add(btnNewButton);
		con.add(input);
		con.add(output);
		
}
	public static void readFile(File file) {
		
		// TODO Auto-generated method stub
		 String abc="";
		 BufferedReader reader=null;
		 try {
			 reader= new BufferedReader(new FileReader(file));
			 String temp ="";
			 
			 String name="";
			 String def="P:"+"<br>";
			 map = new HashMap<String, ArrayList<String>>();
			 
			 lList=new ArrayList<String>();
			 temp=reader.readLine();
			 abc+=temp;
			 temp=reader.readLine();
			 abc+="=({"+temp+"}"+",{";
			 
			 while((temp=reader.readLine())!=null) {
				 def+=temp+"<br>";
				 String []split=temp.split("\\|");
				 name=split[0].substring(0,split[0].indexOf(":") );
				 ArrayList<String> rList=new ArrayList<String>();
				 lList.add(name);
				 rList.add(split[0].substring(split[0].indexOf("=")+1, split[0].length()));
				 
				 for(int n=1;n<split.length;n++) {
					 rList.add(split[n]);
					
				 }
				 map.put(name, rList);
				 
			 }
			 
			
			 
			    for(String string :findt()) {
			      abc+=string+",";
			    }
			    abc=abc.substring(0, abc.length()-1);
			    abc+="},P,"+abc.substring(abc.indexOf("[")+1,abc.indexOf("]"))+")"; 	
			    input.setText("<html><body>"+abc+"<br>"+def+"</body></html>");	 
		 }catch(IOException e){
			 e.printStackTrace();
		 }
		 
		 boolean temp2=false;
		 boolean temp3=false;
		 boolean temp4=true;
		 for(String string:lList) {
			 if(isNonTerminal(string))
				 temp2=true;
			 System.out.println(string);
			 System.out.println(abc.substring(abc.indexOf("[")+1,abc.indexOf("]")));
			 if(string.equals(abc.substring(abc.indexOf("[")+1,abc.indexOf("]")))) {
				 temp3=true;
				 
			 }
			
			 if(string.charAt(0)=='ε')
				 temp4=false;
			 
		 }
		 if(temp2&&temp3&&temp4)
		 classify();
		 else {
			 if(!temp2)
			 output.setText("左侧全是终结符！");
			 
			 if(!temp3)
				 output.setText("左侧没有起始符号出现！");	
			 
			 if(!temp4)
		    	 output.setText("左侧有ε空串!");
			 } 
	}
	
	
	public static ArrayList<String> findt() {
		tList=new ArrayList<String>();
		boolean temp=true;
		for(String string :lList) {
			for(String string2: map.get(string)) {
				if(isTerminal(string2)) {
					
				for(String string3:tList) {
					if(string3.equals(string2)||(string2.charAt(0)=='ε'))
						temp=false;			
				}
				if(temp) 
				tList.add(string2);
				temp=true;
				
				}
								
			}
		 }	
		return tList;
	}
	
	
	
	public static boolean classify() {
		for(String string :lList) {
			if(!isNonTerminal(string)) {
				
				output.setText(classify1());
			    return false;
			}
		}
		
		output.setText(classify3());
		
		return true;
	}
	/*else {
	 * if(!classify3(string)) {
		output.setText("是二型文法");*/
	public static String classify1() {
		for(String string:lList) {
			for(String string2:map.get(string)) {
				if((string2.length()<string.length())||string2.charAt(0)=='ε')
				return "是0型文法";
				
			}
		}
	     return "是1型文法";
	
		
	}
	
	public static String classify3() {
		for(String string:lList)
		for(String string2:map.get(string)) {
			
			if(string2.length()==2&&(string2.charAt(0)=='ε'))
				return "ε不能出现在非终结符的左侧";
		if(!(string2.length()==1&&isTerminal(string2))
				&&!(string2.length()==2&&(string.charAt(0)!='ε')&&isTerminal(string2.substring(0, 1))&&isNonTerminal(string2.substring(1,2)))) {
			
			    return classify2();
			
		
		}
	
		}
		
		return classify4();
		
	}
	
	public static String classify2() {
		for(String string:lList)
			for(String string2:map.get(string)) {
				if(string2.length()==1&&string2.charAt(0)=='ε')
					return "是扩充2型文法";
			}
		return "是2型文法";
	}
	
	public static String classify4() {
		for(String string:lList)
			for(String string2:map.get(string)) {
				if(string2.length()==1&&string2.charAt(0)=='ε')
					return "是扩充3型文法";
			}
		return "是3型文法";
	}
	
	public static boolean isTerminal(String string) {
		if(string.length()==1&&((string.charAt(0)>='a'&&string.charAt(0)<='z')||
				(string.charAt(0)>='0'&&string.charAt(0)<='9')||string.charAt(0)=='ε'))
			return true;
		return false;
	}
	
	public static boolean isNonTerminal(String string) {
		if(string.length()==1&&(string.charAt(0)>='A'&&string.charAt(0)<='Z')) 
			return true;
		return false;
			
		
	}
	
}

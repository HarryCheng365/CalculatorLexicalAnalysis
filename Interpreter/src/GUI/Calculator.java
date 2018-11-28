package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import exception.SyntaxException;
import main.Main;


public class Calculator {

	private JFrame frame;
	private JTextField textField;
	private JLabel statusLabel;
	private JButton cal; 
	private JLabel resultLabel;
	private JPanel imagePanel;
	private String abc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculator window = new Calculator();
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
	public Calculator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1024, 768);
		frame.setTitle("Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(frame.isVisible()==false)
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			ImageIcon bgp=new ImageIcon("/Users/Haoyu/Documents/CalculatorLexicalAnalysis/Interpreter/src/GUI/desktop.png");
			JLabel bgl=new JLabel(bgp);
			frame.getLayeredPane().setLayout(null);
			frame.getLayeredPane().add(bgl, new Integer(Integer.MIN_VALUE));
			bgl.setBounds(0,0,bgp.getIconWidth(), bgp.getIconHeight());
					
		
			imagePanel = (JPanel) frame.getContentPane();
			imagePanel.setOpaque(false);
			JRootPane jp1 = (JRootPane) frame.getRootPane();
			jp1.setOpaque(false);
			
			abc="                                                                              ";
		Container con=frame.getContentPane();
	    con.setLayout(new GridLayout(5,1));
	    
	    JPanel panel1 = new JPanel();	
		JLabel title = new JLabel("Welcome to Calculator !");
		title.setVerticalAlignment(SwingConstants.BOTTOM);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Apple Chancery", Font.BOLD, 54));
		panel1.add(title);
		panel1.setOpaque(false);

		con.add(panel1);
		
		JPanel panel2=new JPanel();
		JLabel input=new JLabel("Input:");
		input.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel2.add(input);
		textField = new JTextField(30);
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		textField.setBackground(UIManager.getColor("Button.background"));
		textField.setOpaque(false); 
        textField.setBorder(new EmptyBorder(0,0,0,0));  

		panel2.add(textField);
		panel2.setOpaque(false);
		con.add(panel2);
		
		JPanel panel3=new JPanel();
		JLabel exception=new JLabel("Exception:");
		exception.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel3.add(exception);
		statusLabel=new JLabel(abc);
		statusLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		statusLabel.setForeground(Color.red);
		panel3.add(statusLabel);
		panel3.setOpaque(false);
		con.add(panel3);
		
		JPanel panel4=new JPanel();
		JLabel output=new JLabel("Result:");
		output.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel4.add(output);
		resultLabel=new JLabel(abc);
		resultLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		resultLabel.setForeground(Color.red);
		panel4.add(resultLabel);
		con.add(panel4);
		
		ImageIcon image4= new ImageIcon("/Users/Haoyu/eclipse-workspace/IBook/Image/register.png");
		JPanel Bpanel=new JPanel();
		cal=new JButton("Calculate",image4);
		cal.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		cal.setVerticalAlignment(SwingConstants.TOP);
		cal.setHorizontalAlignment(SwingConstants.LEFT);
		cal.setOpaque(false);
	    cal.setBorderPainted(false);
	    cal.addActionListener(new CalCulatorListener());
		Bpanel.add(cal);
		Bpanel.setOpaque(false);
		con.add(Bpanel);
	}
	class CalCulatorListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	try {
	    		Main.writeTextFile(textField.getText()+";");
				resultLabel.setText(Main.Interpreter().substring(1, Main.Interpreter().indexOf(',')));
				statusLabel.setText(abc);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				statusLabel.setText(e1.getMessage());
				resultLabel.setText(abc);
				
			}
            
	      	
	      	
	    }
	}

}

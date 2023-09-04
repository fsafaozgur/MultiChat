package multiChat;




import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class ChatServer {

	
	 private static JTextField port = new JTextField();
	 private static JTextField ileti = new JTextField();
	 private static JTextArea message = new JTextArea();
	 private static JScrollPane scroll = new JScrollPane();
	 private static JButton tus = new JButton("Baþlat");
	 private static JButton tus1 = new JButton("Gönder");
	 public static JFrame frame = new JFrame();
	 private static ServerSocket s;
	 private static Socket s1;
	 private static int portNumarasi;
	 public static ArrayList<DataOutputStream> dosList=new ArrayList<DataOutputStream>();
	 public static ArrayList<String> rumuzList=new ArrayList<String>();
	
	 
	 
	public static void baglan(int portNumara) { 
		
		try {
			s = new ServerSocket(portNumara);
			
			while(true)
			{
			try {
				s1=s.accept();
				ChatThread2 chat = new ChatThread2(s1);
				chat.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			s1=null;
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Baðlanýlamadý Lütfen Bilgileri Kontrol Edin");
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args)  throws InterruptedException
	{


		
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame();
		frame.setTitle("Fatiqua Sohbet v1.0 (Server)");
		frame.setLocation(500, 150);
		frame.setSize(470, 610);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	
		JPanel panel = new JPanel();
		panel.setLocation(0, 0); 	
		panel.setSize(600, 800);	 	
		panel.setLayout(null);
		

		
		
		/* --------------------------------------------- */
		
		
		JLabel portLabel = new JLabel("Port Numarasý:");
		portLabel.setLocation(20, 10);
		portLabel.setSize(170, 30);
		portLabel.setHorizontalAlignment(2);
		panel.add(portLabel);
		
		
		
		//JTextField port = new JTextField();
		port.setSize(250, 30);
		port.setLocation(180, 10);
		panel.add(port);
		
	



		/*---------------------------------------------*/
		
		
		//JButton tus = new JButton("Baðlan");
		tus.setSize(85, 30);
		tus.setLocation(342, 90);
		
		tus.addActionListener(new ActionListener(){
			
			
			public void actionPerformed(ActionEvent arg0) {
				
				if ( port.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Boþ alan býrakmayýnýz");	
				}
				
				
				else
				{
					
					try {
					portNumarasi = Integer.valueOf(port.getText());
					baglan(portNumarasi);
					}catch (Exception e) {
						JOptionPane.showMessageDialog(frame, "Baðlantý kurulamadý");	
		                e.printStackTrace();
					}
					}
					
				}
				
			
			
			
		});
		panel.add(tus);
		
		
		
		
		/* --------------------------------------------- */

		//message.setLocation(10, 200);
		//message.setSize(420, 300);
		message.setBounds(20, 150, 410, 300);
		message.setEditable(false);
		Font font = message.getFont();  
		message.setFont(font.deriveFont(Font.BOLD));
		DefaultCaret caret = (DefaultCaret)message.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(20, 150, 410, 300);
		scroll.getViewport().setBackground(Color.WHITE);
		scroll.getViewport().add(message);

		panel.add(scroll);
		
		
		/* --------------------------------------------- */
		
		JLabel iletiLabel = new JLabel("Mesaj:");
		iletiLabel.setLocation(20, 470);
		iletiLabel.setSize(170, 30);
		iletiLabel.setHorizontalAlignment(2);
		panel.add(iletiLabel);
		
		
		
		//JTextField ileti = new JTextField();
		ileti.setSize(250, 30);
		ileti.setLocation(77, 470);	
		ileti.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	/*if (ileti.getText() != null)
					{
					try {
						
						sohbetGonder(dos, ileti.getText());
						ileti.setText("");
						
					} catch (IOException a) {
						a.printStackTrace();
					}
				}*/
	            }
	        }

	    });
		panel.add(ileti);


		/* --------------------------------------------- */
		

		
		
		//JButton tus = new JButton("Gönder");
		tus1.setSize(85, 30);
		tus1.setLocation(344, 470);
		
		tus1.addActionListener(new ActionListener(){
			
			
			public void actionPerformed(ActionEvent arg0) {
				
			/*	if (ileti.getText().isEmpty())
				{
						
				}
				else
				{
				try {
					
					sohbetGonder(dos, ileti.getText());
					ileti.setText("");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
			
			}
		});
		panel.add(tus1);
		
		
		
		
		/* --------------------------------------------- */
		
		
		

		frame.add(panel);
		frame.setVisible(true);


	}
	
	
}

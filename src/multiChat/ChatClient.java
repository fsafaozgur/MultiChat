package multiChat;



import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;


public class ChatClient extends Thread {


private static JTextField ip = new JTextField();
private static JTextField port = new JTextField();
private static JTextField kullanici = new JTextField();
private static JTextField ileti = new JTextField();
private static JTextArea message = new JTextArea();
private static JTextArea kisiler = new JTextArea();
private static JScrollPane scroll = new JScrollPane();
private static JScrollPane scroll1 = new JScrollPane();
private static JButton tus = new JButton("Baðlan");
private static JButton tus1 = new JButton("Gönder");
private static JFrame frame = new JFrame();
private static Socket s;
private static DataOutputStream dos;
private static DataInputStream dis;
private static String rumuz;
private static ChatClient client;

public static void baglan(String ipadres, int portNumara) throws IOException
{
	
	try {
		s = new Socket(ipadres, portNumara);
		InputStream s1in = s.getInputStream();
		dis = new DataInputStream(s1in);



		OutputStream s1out = s.getOutputStream();
		dos = new DataOutputStream(s1out);
	} catch (IOException e) {
		e.printStackTrace();
	}finally {
		client = new ChatClient();
		
		client.start();
	}
}






@SuppressWarnings("deprecation")
public static void sohbetGonder(DataOutputStream dos, String veri) throws IOException
{
	String yolla;
	
	if (veri.equals("/çýkýþ"))
	{
	try {
		
		dos.writeUTF(rumuz+": " +veri);
		dis.close();
		dos.close();
		s.close();
		client.stop();
		ip.setText("");
		kullanici.setText("");
		port.setText("");
		message.setText("");
		message.setVisible(false);
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	if (!veri.equals("/çýkýþ")) {
	yolla=(rumuz+": " +veri);
	
	dos.writeUTF(yolla);
	
	}
	
}



public void run() {
	String st = null;
	String satir = "\n";
	while(true) {
	try {
		
		st = dis.readUTF();
		} catch (IOException e) {
		e.printStackTrace();
	}finally {
	if (st != null && !st.equals("Server çevrimdýþý."))
	{
	
	message.append(st+satir);
	for(String a: ChatServer.rumuzList)
	{
		kisiler.append(a+satir);
	}

	}
	if (st.equals("Server çevrimdýþý."))
	{
	try {
		dis.close();
		dos.close();
		s.close();
	} catch (IOException e) {
		e.printStackTrace();
	}

	}
	}
	}
	}



public static void main(String[]  args) throws InterruptedException
{
	

	
	JFrame.setDefaultLookAndFeelDecorated(true);
	frame = new JFrame();
	frame.setTitle("Fatiqua Sohbet v1.0");
	frame.setLocation(500, 150);
	frame.setSize(470, 610);
	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(false);

	JPanel panel = new JPanel();
	panel.setLocation(0, 0); 	
	panel.setSize(600, 800);	 	
	panel.setLayout(null);
	
	
	JLabel ipLabel = new JLabel("Ýp Adresi");
	ipLabel.setLocation(20, 10);
	ipLabel.setSize(170, 30);
	ipLabel.setHorizontalAlignment(2);
	panel.add(ipLabel);

	//JTextField ip = new JTextField();
	//ip.setText("localhost");
	ip.setSize(250, 30);
	ip.setLocation(180, 10);
	ip.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ip.getText().equals("Baðlanacaðýnýz sunucunun IP adresini girin"))
			{
				ip.setText("");
			}
			
		}
		
		
	});
	panel.add(ip);
	
	
	/* --------------------------------------------- */
	
	
	JLabel portLabel = new JLabel("Port Numarasý:");
	portLabel.setLocation(20, 50);
	portLabel.setSize(170, 30);
	portLabel.setHorizontalAlignment(2);
	panel.add(portLabel);
	
	
	
	//JTextField port = new JTextField();
	port.setSize(250, 30);
	port.setLocation(180, 50);
	panel.add(port);
	
	
	
	/* --------------------------------------------- */
	
	JLabel kullaniciLabel = new JLabel("Kullanýcý Ýsminiz:");
	kullaniciLabel.setLocation(20, 90);
	kullaniciLabel.setSize(170, 30);
	kullaniciLabel.setHorizontalAlignment(2);
	panel.add(kullaniciLabel);
	
	
	
	//JTextField kullanici = new JTextField();
	kullanici.setSize(250, 30);
	kullanici.setLocation(180, 90);
	panel.add(kullanici);


	/*---------------------------------------------*/
	
	
	//JButton tus = new JButton("Baðlan");
	tus.setSize(85, 30);
	tus.setLocation(342, 130);
	
	tus.addActionListener(new ActionListener(){
		
		
		public void actionPerformed(ActionEvent arg0) {
			
			if (kullanici.getText().isEmpty() || port.getText().isEmpty() || ip.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(frame, "Boþ alan býrakmayýnýz");	
			}else
			{
				
				rumuz = kullanici.getText();
				try {
				int a = Integer.valueOf(port.getText());
				String ipadres = ip.getText();
				baglan(ipadres, a);
				tus.setVisible(false);
				//*********
				
				panel.remove(portLabel);
				panel.remove(port);
				panel.remove(ipLabel);
				panel.remove(ip);
				panel.remove(kullaniciLabel);
				panel.remove(kullanici);
				panel.remove(tus);
				
				JLabel kisi = new JLabel("Kiþiler:");
				kisi.setLocation(20, 10);
				kisi.setSize(50, 30);
				kisi.setHorizontalAlignment(2);
				panel.add(kisi);
				
				
				kisiler.setBounds(80, 20, 150, 150);
				kisiler.setEditable(false);
				Font font = kisiler.getFont();  
				kisiler.setFont(font.deriveFont(Font.BOLD));
				//message.setForeground(Color.BLUE);
				
				DefaultCaret caret1 = (DefaultCaret)kisiler.getCaret();  //bunlar autoscroll için
				caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		//bunlar autoscroll için
				scroll1 = new JScrollPane();
				scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll1.setBounds(80, 20, 150, 150);
				scroll1.getViewport().setBackground(Color.WHITE);
				scroll1.getViewport().add(kisiler);

				panel.add(scroll1);
				panel.revalidate();
				panel.repaint();
				
				//*********
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
	message.setBounds(20, 200, 410, 300);
	message.setEditable(false);
	Font font = message.getFont();  
	message.setFont(font.deriveFont(Font.BOLD));
	//message.setForeground(Color.BLUE);
	
	DefaultCaret caret = (DefaultCaret)message.getCaret();  //bunlar autoscroll için
	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		//bunlar autoscroll için
	scroll = new JScrollPane();
	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	scroll.setBounds(20, 200, 410, 300);
	scroll.getViewport().setBackground(Color.WHITE);
	scroll.getViewport().add(message);

	panel.add(scroll);
	
	
	/* --------------------------------------------- */
	
	JLabel iletiLabel = new JLabel("Mesaj:");
	iletiLabel.setLocation(20, 517);
	iletiLabel.setSize(170, 30);
	iletiLabel.setHorizontalAlignment(2);
	panel.add(iletiLabel);
	
	
	
	//JTextField ileti = new JTextField();
	ileti.setSize(250, 30);
	ileti.setLocation(77, 520);
	ileti.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
            	if (ileti.getText().isEmpty())
    			{
    					
    			}
            	else{
            		if (message.getRows()== 15)
            		{
            			message.setText(" ");
            		}else {
				try {
					
					sohbetGonder(dos, ileti.getText());
					ileti.setText("");
					
				} catch (IOException a) {
					a.printStackTrace();
				}
			}
            }
        }
        }
    });
	panel.add(ileti);


	/* --------------------------------------------- */
	

	
	
	//JButton tus = new JButton("Gönder");
	tus1.setSize(85, 30);
	tus1.setLocation(344, 520);
	
	tus1.addActionListener(new ActionListener(){
		
		
		public void actionPerformed(ActionEvent arg0) {
			
			if (ileti.getText().isEmpty())
			{
					
			}
			else {
			try {
				
				
				sohbetGonder(dos, ileti.getText());
				ileti.setText("");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		}
	});
	panel.add(tus1);
	

	
	
	/* --------------------------------------------- */
	
	
	
	
	frame.addWindowListener(new java.awt.event.WindowAdapter() {
	    @Override
	    public void windowClosing(java.awt.event.WindowEvent e) {


	    	try {
				dos.writeUTF(rumuz+": /çýkýþ" );
				dis.close();
				dos.close();
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			client.stop();

	        e.getWindow().dispose();
	        
	    }
	});
	
	
	/* --------------------------------------------- */
	

	frame.add(panel);
	frame.setVisible(true);

	
}	
	
}	
	

package multiChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class ChatThread2 extends Thread{


	private static  Socket s;

	
	ChatThread2(Socket soket)
	{
	ChatThread2.s= soket;	
	}
	
	
	@SuppressWarnings("deprecation")
	public void baglantiKes(Socket s, DataInputStream dis, DataOutputStream dos)
	{
		try {
			
			dos.close();
			this.stop();
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChatServer.dosList.remove(dos);
		
	}
	
	
	public void run()
	{
	InputStream s1in;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	String st = null;
	
	try {
		s1in = s.getInputStream();
		dis = new DataInputStream(s1in);
		OutputStream s1out = s.getOutputStream();
		dos = new DataOutputStream(s1out);
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	
	
	ChatServer.dosList.add(dos);

	
	
	while(true) {
			
		try {
			
			st = dis.readUTF();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			String son = null;
			String[] nick = null;
			int esit = 0;
			nick = st.split(":");
			
			
			
			//*********
			 
			if (ChatServer.rumuzList.isEmpty())
			{
				ChatServer.rumuzList.add(nick[0]);
			}
			
			if (!ChatServer.rumuzList.isEmpty())
			{
			for (String a: ChatServer.rumuzList)
			{
			if (a.equals(nick[0]))
			{
			esit=1;
			break;
			
			}
			}
			}
			
			if (esit == 0)
			{
			ChatServer.rumuzList.add(nick[0]);	
			}
			
			
			
			//*********
			
			int bolUzunluk= st.length();
			
			
			if(bolUzunluk >8 )
			{
			son=st.substring(bolUzunluk-6);
			}else if (bolUzunluk <= 8)
			{
			son=" ";	
			}
		if (st != null && !son.equals("/çýkýþ"))
		{
		
			for (DataOutputStream os: ChatServer.dosList)	
			{
				
				try {
					
					os.writeUTF(st);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		if (son.equals("/çýkýþ"))
		{
		break;
		}
		}
		}
	
	baglantiKes(s, dis, dos);
	System.exit(0);	
	}
	
	
	
	}
	
	
	
	

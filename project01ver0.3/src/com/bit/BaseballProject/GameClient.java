package com.bit.BaseballProject;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameClient extends Frame implements ActionListener{
	
	TextArea chat;		// ä��â
	TextField inputnum;	// ���� �Է��ϴ� ���� ������ â
	TextField chatinput;// ä�� �Է�â
	TextArea resul;	// ��� ǥ��â
	
	Panel p1;			// 1~9 ���� ��ư �г�
	Panel p2;			// start,end ��ư �г�
	Panel p3;			// ta �߰��� �г� 
	Panel p4;			// input �߰��� �г�
	Panel img;			// �̹��� �߰��� �г�
	Image backimg;		// ��� �̹��� �߰��� �г�
	Panel result;		// ��� ǥ���� �г�
	Panel textFildChat;
	Panel TextAreaChat;
	
	JButton[] btn;		// 1~9 ���� ��ư
	JButton[] btn1;		// start, end ��ư
	JButton btngo;		// Go ��ư
	JButton reset;
	String[] nums = {"1","2","3","4","5","6","7","8","9","0"};
	String number;
	String results;
	String disp = "";
	StringBuffer str;
	Label dispL;
	
	
	InputStream is = null;
	OutputStream os = null;
	BufferedReader br = null;
	BufferedWriter bw = null;
	
	
	
	public GameClient(){
		
		this.setLayout(null);
		
		// 1~9 ��ư �����
		
		p1 = new Panel();
		
		btn = new JButton[10];
		
		for(int i=0; i<btn.length; i++){
			p1.setLayout(new FlowLayout(FlowLayout.LEFT));
			btn[i] = new JButton(nums[i]);
			btn[i].setPreferredSize(new Dimension(75, 50));
			p1.add(btn[i]);
			
		}
		for(int i=0; i<btn.length; i++){
			btn[i].setEnabled(false);
			
		}
		
		p1.setSize(405, 115);
		p1.setLocation(50, 440);
		
		
		// start,end ��ư �����
		
		p2 = new Panel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		btn1 = new JButton[2];
		btn1[0] = new JButton("START !");
		btn1[0].setPreferredSize(new Dimension(80,50));
		p2.add(btn1[0]);
		btn1[1] = new JButton("END !");
		btn1[1].setPreferredSize(new Dimension(80,50));
		p2.add(btn1[1]);
		
		
		btn1[1].setEnabled(false);
			
		
		
		p2.setSize(90, 115);
		p2.setLocation(460, 440);
		
		
		// �ϴܿ� chatting â �����
		TextAreaChat = new Panel();
		TextAreaChat.setLayout(new FlowLayout());
		
		chat = new TextArea();
		chat.setSize(500, 140);
		chat.setLocation(50, 570);
		chat.setEditable(false);
		
		
		
		// chatting �Է�â chatting â �Ʒ��� �����
		textFildChat = new Panel();
		textFildChat.setLayout(new FlowLayout());
		
		chatinput = new TextField();
		chatinput.setSize(500, 25);
		chatinput.setLocation(50, 710);
		
		textFildChat.add(chatinput);
		
		// input �Էµ� â �����
		
		inputnum = new TextField();
		inputnum.setSize(100, 35);
		inputnum.setLocation(240, 400);
		
		
		// reset ��ư 
		
		reset = new JButton("Reset");
		reset.setSize(67, 35);
		reset.setLocation(400, 400);
		add(reset);
		reset.setEnabled(false);
		
		// go ���� ��ư �����
		
		btngo = new JButton("Go");
		btngo.setSize(50, 35);
		btngo.setLocation(350, 400);
		add(btngo);
		
		btngo.setEnabled(false);
		
		
		// ��ܿ� ��� ǥ�� â ����� (���߿� �̺�Ʈ�� �߰� => textField.settext(string => ������))
		
		resul = new TextArea();
		resul.setLocation(95, 70);
		resul.setSize(400, 150);
		resul.setEditable(false);
		resul.setText(results);
		
		
		
		
		// ���ǥ�� â �Ʒ��� �߱��� �̹��� �ֱ�
		
		img = new Panel();
		img.setLayout(new GridLayout(1,3));
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image image = kit.createImage(3+".jpg");
		Icon icon = new ImageIcon(image);
		
		img.add(new JButton(icon));
		img.add(new JButton(icon));
		img.add(new JButton(icon));
		
		img.setLocation(70, 250);
		img.setSize(450, 104);
		
		// x ������ â ������ �ϱ�
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		// start ������ ���� ���α׷� ����
		
		btn1[0].addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				chat.setEnabled(true);
				chatinput.setEnabled(true);
				resul.setEnabled(true);
				inputnum.setEnabled(true);
				btngo.setEnabled(true);
				reset.setEnabled(true);
				btn1[0].setEnabled(true);
				btn1[1].setEnabled(true);
				for(int i=0; i<btn.length; i++){
					btn[i].setEnabled(true);
					
				}
			}
		});
		
		
		// end ������ ���� ��� �����
		btn1[1].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		// go ��ư ��� �����
		
		
		btngo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "Go"){
					
					int cnt=0;
					++cnt;
					
					if(cnt<5){
						if(disp.length()<4 || disp.length()>4){
							resul.append("�ٽ� �Է��ϼ���. ���� 4�� �Է�!");
							resul.append("\r\n");
							inputnum.setText("");
							disp = "";
						} else{
							
							resul.append("\r\n");
							resul.append("�Է��� ���� >> " + disp);	
							resul.append("\r\n");
							
							try {
								bw.write(disp);
								bw.newLine();
								bw.flush();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						
					} else if(cnt==5){
						resul.append("������ ����Ǿ����ϴ�.");
					}
				}
					inputnum.setText("");
					disp = "";
			}
			
		});
		
		
		// reset ��ư ��� �����
		
		reset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "reset"){
					reset();
				
				}
				inputnum.setText("");
				disp = "";
			}
		});
		
		
		// 1~9 ���� ���� ������ ���� �Է��� �� �ִ� ��� �����
		
		for(int i=0; i<btn.length; i++){
			
			btn[i].addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					
					str = new StringBuffer();
						
					if(e.getActionCommand()=="1"){
						str.append(1);
						disp += str.toString();
					}if(e.getActionCommand() == "2"){
						str.append(2);
						disp += str.toString();
					}if(e.getActionCommand() == "3"){
						str.append(3);
						disp += str.toString();
					}if(e.getActionCommand() == "4"){
						str.append(4);
						disp += str.toString();
					}if(e.getActionCommand() == "5"){
						str.append(5);
						disp += str.toString();
					}if(e.getActionCommand() == "6"){
						str.append(6);
						disp += str.toString();
					}if(e.getActionCommand() == "7"){
						str.append(7);
						disp += str.toString();
					}if(e.getActionCommand() == "8"){
						str.append(8);
						disp += str.toString();
					}if(e.getActionCommand() == "9"){
						str.append(9);
						disp += str.toString();
					}if(e.getActionCommand() == "0"){
						str.append(0);
						disp += str.toString();
						
					}inputnum.setText(disp);
					
				}
			});
		}
		
		
		// ��ü���� Frame �����ϱ�, panel�� add�ϱ�
		
		add(p1);
		add(p2);
		add(chatinput);
		add(chat);
		add(inputnum);
		add(resul);
		add(img);
		setTitle("���� �߱� ���� !");
		setSize(600, 755);
		setLocation(200, 200);
		setVisible(true);
		
	// game client end 
	
	// ��濡 �߱����� �̹��� �ֱ�

		///// Ŭ���̾�Ʈ�� ä��â�� �Է��ϴ� �� TextArea�� ������ �ϱ�
//		chatinput.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					String ip =  "192.168.0.199";
//					int port = 8911;
//					Socket socket = new Socket(ip, port);
//					
//					bw.write(chatinput.getText());
//					InetAddress local = InetAddress.getLocalHost();
//					ip=local.getHostAddress();
//					if(e.getSource() == chatinput){
//						chat.append("["+ip+"]"+": "+chatinput.getText()+"\n");
//						chatinput.selectAll();
//						chatinput.setText("");
//						chatinput.setText("");
//					}
//					bw.newLine();
//					bw.flush();
//					
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			}
//		});
		
	}

	
	
	public static void main(String[] args) {
		
		GameClient me = new GameClient();
		Socket socket = null;
		
		
		try {
			InputStream is = null;
			OutputStream os = null;
			InputStreamReader isr = null;
			OutputStreamWriter osw = null;
			BufferedReader br = null;
			
			me.chat.append("������ ���� ��û��..");
			me.chat.append("\r\n");
			
			InetAddress local = InetAddress.getLocalHost();
			String ip=local.getHostAddress();
			int port = 8911;
			socket = new Socket(ip, port);
//	
			
			InetAddress addr = socket.getInetAddress();
			String user = addr.getHostAddress();
			
			me.chat.append("������ ���� ����!");
			me.chat.append("\r\n");
			me.chat.append(user + " ���� �����߽��ϴ�");
			me.chat.append("\r\n");
			
//			br.readLine();
//			me.chat.append("\r\n");
			
			is = socket.getInputStream();
			os = socket.getOutputStream();
			isr = new InputStreamReader(is);
			osw = new OutputStreamWriter(os);
			br = new BufferedReader(isr);
			me.bw = new BufferedWriter(osw);
			
			String str = "";
			String str1 = "";
			Boolean run = true;
			
			InputStreamReader stdnum = new InputStreamReader(is);
			BufferedReader stdin = new BufferedReader(stdnum);		// ���� �Է��ϴ� ��
			
			// ���� ����
			
			try {
				// start ��ư ������ ��ư �����Ǹ鼭 ���� 
				me.btn1[0].addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						me.chat.setEnabled(true);
						me.chatinput.setEnabled(true);
						me.resul.setEnabled(true);
						me.inputnum.setEnabled(true);
						me.btngo.setEnabled(true);
						me.reset.setEnabled(true);
						me.btn1[0].setEnabled(true);
						me.btn1[1].setEnabled(true);
						for(int i=0; i<me.btn.length; i++){
							me.btn[i].setEnabled(true);
							
						}
					}
				});
			
				while(run){
					
					me.resul.append("\r\n");
					me.resul.append("���ڸ� ���纸����! ");
					me.resul.append("\r\n");
					
					str = me.disp;
					me.bw.write(str);
					me.bw.flush();
					
					// �����κ��� �޼��� �޾ƿͼ� ��� . s, b, cnt
					str1 = br.readLine();
					System.out.println(str1);
					me.resul.append("���: " + str1);
					me.resul.append("\r\n");
					
					if(str1.charAt(0) == '-' || str1.charAt(20) == '-'){
						
						run = false;
						me.reset.setEnabled(false);
						me.btn1[1].setEnabled(false);
						me.btngo.setEnabled(false);
						
						for(int i=0; i<me.btn.length; i++){
							me.btn[i].setEnabled(false);
						}
					}
	
				}// while end 
				
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		}		
	}
	
	
	public void paint(Graphics g){
		backimg = Toolkit.getDefaultToolkit().getImage(4+".jpg");
		if(backimg!=null){
			g.drawImage(backimg, 0, 0, this);
			super.paint(g);
			
		}
	}

	
	public void reset(){
		str.delete(0, str.length());
		
	}
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}







//	@Override
//	public void run() {
//		class PerClientThread extends Thread{
//			final List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());
//
//			Socket socket;
//			
//			PrintWriter writer;
//			public PerClientThread(Socket socket){
//				this.socket = socket;
//			
//				try{
//				writer = new PrintWriter(socket.getOutputStream());
//				list.add(writer);
//				
//				} catch(Exception e){
//					System.out.println(e.getMessage());
//				}
//			}
//
//			public void run(){
//				String name = null;
//				
//				try{
//					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//					InetAddress local = InetAddress.getLocalHost();
//					String ip=local.getHostAddress();
////					name = reader.readLine();
//					sendAll(ip + "is joined");
//					
//					while(true){
//						String str = reader.readLine();
//						if(str == null){
//							break;
//						}
//						sendAll(ip + ">" + str);
//						
//					}
//				
//				} catch(Exception e){
//					System.out.println(e.getMessage());
//				} finally{
//					list.remove(writer);
//					sendAll(name + "is out");
//					try{
//						socket.close();
//					} catch(Exception ignored){}
//				}
//			}
//			
//			
//			private void sendAll(String str) {
//				for(PrintWriter writer:list){
//					writer.println(str);
//					writer.flush();
//				}
//				
//			}
//
//			
//		}
//	}
//
//	
}



//
//class PerClientThread extends Thread{
//	final List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());
//
//	Socket socket;
//	
//	PrintWriter writer;
//	public PerClientThread(Socket socket){
//		this.socket = socket;
//	
//		try{
//		writer = new PrintWriter(socket.getOutputStream());
//		list.add(writer);
//		
//		} catch(Exception e){
//			System.out.println(e.getMessage());
//		}
//	}
//
//	public void run(){
//		String name = null;
//		
//		try{
//			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			InetAddress local = InetAddress.getLocalHost();
//			String ip=local.getHostAddress();
////			name = reader.readLine();
//			sendAll(ip + "is joined");
//			
//			while(true){
//				String str = reader.readLine();
//				if(str == null){
//					break;
//				}
//				sendAll(ip + ">" + str);
//				
//			}
//		
//		} catch(Exception e){
//			System.out.println(e.getMessage());
//		} finally{
//			list.remove(writer);
//			sendAll(name + "is out");
//			try{
//				socket.close();
//			} catch(Exception ignored){}
//		}
//	}
//	
//		me.chatinput.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					bw.write(chatinput.getText());
//					InetAddress local = InetAddress.getLocalHost();
//					String ip=local.getHostAddress();
//					if(e.getSource() == chatinput){
//						chat.append("["+ip+"]"+": "+chatinput.getText()+"\n");
//						chatinput.selectAll();
//						chatinput.setText("");
//						chatinput.setText("");
//					}
//					bw.newLine();
//					bw.flush();
//					
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			}
//		});
//	
//	private void sendAll(String str) {
//		for(PrintWriter writer:list){
//			writer.println(str);
//			writer.flush();
//		}
//		
//	}

	
//}

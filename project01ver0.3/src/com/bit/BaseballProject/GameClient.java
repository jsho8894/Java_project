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
	
	TextArea chat;		// 채팅창
	TextField inputnum;	// 내가 입력하는 숫자 보여줄 창
	TextField chatinput;// 채팅 입력창
	TextArea resul;	// 결과 표시창
	
	Panel p1;			// 1~9 숫자 버튼 패널
	Panel p2;			// start,end 버튼 패널
	Panel p3;			// ta 추가할 패널 
	Panel p4;			// input 추가할 패널
	Panel img;			// 이미지 추가할 패널
	Image backimg;		// 배경 이미지 추가할 패널
	Panel result;		// 결과 표시할 패널
	Panel textFildChat;
	Panel TextAreaChat;
	
	JButton[] btn;		// 1~9 숫자 버튼
	JButton[] btn1;		// start, end 버튼
	JButton btngo;		// Go 버튼
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
		
		// 1~9 버튼 만들기
		
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
		
		
		// start,end 버튼 만들기
		
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
		
		
		// 하단에 chatting 창 만들기
		TextAreaChat = new Panel();
		TextAreaChat.setLayout(new FlowLayout());
		
		chat = new TextArea();
		chat.setSize(500, 140);
		chat.setLocation(50, 570);
		chat.setEditable(false);
		
		
		
		// chatting 입력창 chatting 창 아래에 만들기
		textFildChat = new Panel();
		textFildChat.setLayout(new FlowLayout());
		
		chatinput = new TextField();
		chatinput.setSize(500, 25);
		chatinput.setLocation(50, 710);
		
		textFildChat.add(chatinput);
		
		// input 입력될 창 만들기
		
		inputnum = new TextField();
		inputnum.setSize(100, 35);
		inputnum.setLocation(240, 400);
		
		
		// reset 버튼 
		
		reset = new JButton("Reset");
		reset.setSize(67, 35);
		reset.setLocation(400, 400);
		add(reset);
		reset.setEnabled(false);
		
		// go 전송 버튼 만들기
		
		btngo = new JButton("Go");
		btngo.setSize(50, 35);
		btngo.setLocation(350, 400);
		add(btngo);
		
		btngo.setEnabled(false);
		
		
		// 상단에 결과 표시 창 만들기 (나중에 이벤트로 추가 => textField.settext(string => 결과출력))
		
		resul = new TextArea();
		resul.setLocation(95, 70);
		resul.setSize(400, 150);
		resul.setEditable(false);
		resul.setText(results);
		
		
		
		
		// 결과표시 창 아래에 야구공 이미지 넣기
		
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
		
		// x 누르면 창 닫히게 하기
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		// start 누르면 게임 프로그램 시작
		
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
		
		
		// end 누르면 종료 기능 만들기
		btn1[1].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		// go 버튼 기능 만들기
		
		
		btngo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "Go"){
					
					int cnt=0;
					++cnt;
					
					if(cnt<5){
						if(disp.length()<4 || disp.length()>4){
							resul.append("다시 입력하세요. 숫자 4개 입력!");
							resul.append("\r\n");
							inputnum.setText("");
							disp = "";
						} else{
							
							resul.append("\r\n");
							resul.append("입력한 숫자 >> " + disp);	
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
						resul.append("게임이 종료되었습니다.");
					}
				}
					inputnum.setText("");
					disp = "";
			}
			
		});
		
		
		// reset 버튼 기능 만들기
		
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
		
		
		// 1~9 까지 숫자 누르면 숫자 입력할 수 있는 기능 만들기
		
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
		
		
		// 전체적인 Frame 구상하기, panel들 add하기
		
		add(p1);
		add(p2);
		add(chatinput);
		add(chat);
		add(inputnum);
		add(resul);
		add(img);
		setTitle("숫자 야구 게임 !");
		setSize(600, 755);
		setLocation(200, 200);
		setVisible(true);
		
	// game client end 
	
	// 배경에 야구게임 이미지 넣기

		///// 클라이언트가 채팅창에 입력하는 거 TextArea에 나오게 하기
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
			
			me.chat.append("서버에 접속 요청중..");
			me.chat.append("\r\n");
			
			InetAddress local = InetAddress.getLocalHost();
			String ip=local.getHostAddress();
			int port = 8911;
			socket = new Socket(ip, port);
//	
			
			InetAddress addr = socket.getInetAddress();
			String user = addr.getHostAddress();
			
			me.chat.append("서버에 접속 성공!");
			me.chat.append("\r\n");
			me.chat.append(user + " 님이 접속했습니다");
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
			BufferedReader stdin = new BufferedReader(stdnum);		// 내가 입력하는 수
			
			// 게임 시작
			
			try {
				// start 버튼 누르면 버튼 해제되면서 시작 
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
					me.resul.append("숫자를 맞춰보세요! ");
					me.resul.append("\r\n");
					
					str = me.disp;
					me.bw.write(str);
					me.bw.flush();
					
					// 서버로부터 메세지 받아와서 출력 . s, b, cnt
					str1 = br.readLine();
					System.out.println(str1);
					me.resul.append("결과: " + str1);
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

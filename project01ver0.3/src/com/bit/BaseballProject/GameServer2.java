package com.bit.BaseballProject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer2 {
	
	public static void main(String[] args){
		  
		  
		ServerSocket serverSocket = null;
		Socket socket = null;
  
		try {
			// 1. 서버소켓 생성
			
			serverSocket = new ServerSocket(8911);
			while(true){
				
				// 2. 클라이언트의 접속요청을 대기한다.
				
				System.out.println("서버 접속 대기 중");
				socket = serverSocket.accept();
				
				System.out.println("클라이언트 접속함");
    
				// 별도 쓰레드 만들어 자료 송수신하게 한다. - 여러 접속자를 처리하기 위한 쓰레드
				
				EchoThread echothread = new EchoThread(socket);
				echothread.start();
			}
   
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				// 4. 소켓 닫기 --> 연결 끊기
				socket.close();
			}catch(IOException e) {e.printStackTrace();}
		}
 	}
}


//쓰레드 클래스
class EchoThread extends Thread{
	static ArrayList<OutputStream> list = new ArrayList<OutputStream>();

	Socket socket;
	ServerSocket serv = null;

	InputStream is=null;
	BufferedReader br=null;
	OutputStream os=null;
	PrintWriter pw=null;

	EchoThread(){}
	EchoThread(Socket socket){
		this.socket = socket;
		try{
			// 소켓으로 부터 송수신을 위한 i/o stream 을 얻기
			
			is = socket.getInputStream(); //수신 --> read();
			br = new BufferedReader(new InputStreamReader(is));
			
			os = socket.getOutputStream(); //송신 --> write();
			pw = new PrintWriter(os);
			list.add(os);
			
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}
	
	// 게임 구동
	public void run(){

		BaseballGame zz;
		InputStream is = null;
		OutputStream os = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			String str = "";
			int cnt=0;
			boolean val=false;
			char val1;
			String cnt1=null;
			int x,y;
	   
			zz = new BaseballGame();  //야구게임 클래스 객체 생성
			zz.randomInt();
	   
			while(!val){		//val = false
				
				is = socket.getInputStream();
				os = socket.getOutputStream();
				isr = new InputStreamReader(is);
				osw = new OutputStreamWriter(os);
				br = new BufferedReader(isr);
				bw = new BufferedWriter(osw);
				
		    /*********************************/
				
				str=br.readLine(); //라인단위로 받아서 스트링에 저장 
				
//				if(str.length() <4 || str.length() > 4){
//					pw.print("다시 입력하세요. 숫자 4개 입력!");
//					pw.flush();
//					
//					str = br.readLine();
//				}
				
				
			    System.out.println(str);
			    zz.inputUserNumber1(str); //입력 받은 숫자를 배열에 담음.
			    
			    x=zz.getX();
			    y=zz.getY();
			    cnt = zz.getCount(); //카운트 처리
			    
			    if(cnt<10) cnt1="0"+String.valueOf(cnt);
			    else cnt1=String.valueOf(cnt);
			    
			    val = zz.getValue(); //종료처리
			    if(val) val1 = 'T';
			    else val1 = 'F';
			    char out = '-';
			    
			    if(cnt == zz.MAX_INPUT){
			    	
			    	pw.print(x+" Strike!  "+y+" Ball!  " + out);
			    	pw.println("시도 횟수 끝! 다시 도전하세요");
			    	pw.flush();
			    	
			    	System.out.println(" 시도 횟수 끝! 다시 도전하세요 "); 
			    	break;
			    	
			    }else if(val1 == 'T'){
			    	pw.print(out + "정답입니다!");
			    	pw.flush();
			    	
			    	System.out.println("정답입니다!"); 
			    	break;
			    	
			    }else{
			    	val1 = 'F';
			    	System.out.println(Thread.currentThread()+" : "+str+" :"+x+"S "+y+"B"+" "+val1+" "+cnt1);
				    pw.println(x+" Strike!  "+y+" Ball!  "+" 5번의 기회 중, "+cnt1 + " 번째 도전중!! "); //출력  1S 2B F 
				    pw.flush();
				    
			    }
			    
		   }// while end
 
		} catch (IOException e) {
		   System.out.println("데이터 송수신에러");
		   e.printStackTrace();
		} finally{
		   close();
		}
	} 
	
	
	public void close(){
		try {
		  // 4. 소켓 닫기 --> 연결 끊기
		  
		   if(br!=null)br.close();
		   if(pw!=null)pw.close();
		   if(os!=null)os.close();
		   if(is!=null)is.close();
		   if(socket!=null)socket.close();
		  }catch(IOException e) {
		   System.out.println("close에러");
		   e.printStackTrace();
		  }
	}
}//스레드 클라스 end  

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
			// 1. �������� ����
			
			serverSocket = new ServerSocket(8911);
			while(true){
				
				// 2. Ŭ���̾�Ʈ�� ���ӿ�û�� ����Ѵ�.
				
				System.out.println("���� ���� ��� ��");
				socket = serverSocket.accept();
				
				System.out.println("Ŭ���̾�Ʈ ������");
    
				// ���� ������ ����� �ڷ� �ۼ����ϰ� �Ѵ�. - ���� �����ڸ� ó���ϱ� ���� ������
				
				EchoThread echothread = new EchoThread(socket);
				echothread.start();
			}
   
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				// 4. ���� �ݱ� --> ���� ����
				socket.close();
			}catch(IOException e) {e.printStackTrace();}
		}
 	}
}


//������ Ŭ����
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
			// �������� ���� �ۼ����� ���� i/o stream �� ���
			
			is = socket.getInputStream(); //���� --> read();
			br = new BufferedReader(new InputStreamReader(is));
			
			os = socket.getOutputStream(); //�۽� --> write();
			pw = new PrintWriter(os);
			list.add(os);
			
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}
	
	// ���� ����
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
	   
			zz = new BaseballGame();  //�߱����� Ŭ���� ��ü ����
			zz.randomInt();
	   
			while(!val){		//val = false
				
				is = socket.getInputStream();
				os = socket.getOutputStream();
				isr = new InputStreamReader(is);
				osw = new OutputStreamWriter(os);
				br = new BufferedReader(isr);
				bw = new BufferedWriter(osw);
				
		    /*********************************/
				
				str=br.readLine(); //���δ����� �޾Ƽ� ��Ʈ���� ���� 
				
//				if(str.length() <4 || str.length() > 4){
//					pw.print("�ٽ� �Է��ϼ���. ���� 4�� �Է�!");
//					pw.flush();
//					
//					str = br.readLine();
//				}
				
				
			    System.out.println(str);
			    zz.inputUserNumber1(str); //�Է� ���� ���ڸ� �迭�� ����.
			    
			    x=zz.getX();
			    y=zz.getY();
			    cnt = zz.getCount(); //ī��Ʈ ó��
			    
			    if(cnt<10) cnt1="0"+String.valueOf(cnt);
			    else cnt1=String.valueOf(cnt);
			    
			    val = zz.getValue(); //����ó��
			    if(val) val1 = 'T';
			    else val1 = 'F';
			    char out = '-';
			    
			    if(cnt == zz.MAX_INPUT){
			    	
			    	pw.print(x+" Strike!  "+y+" Ball!  " + out);
			    	pw.println("�õ� Ƚ�� ��! �ٽ� �����ϼ���");
			    	pw.flush();
			    	
			    	System.out.println(" �õ� Ƚ�� ��! �ٽ� �����ϼ��� "); 
			    	break;
			    	
			    }else if(val1 == 'T'){
			    	pw.print(out + "�����Դϴ�!");
			    	pw.flush();
			    	
			    	System.out.println("�����Դϴ�!"); 
			    	break;
			    	
			    }else{
			    	val1 = 'F';
			    	System.out.println(Thread.currentThread()+" : "+str+" :"+x+"S "+y+"B"+" "+val1+" "+cnt1);
				    pw.println(x+" Strike!  "+y+" Ball!  "+" 5���� ��ȸ ��, "+cnt1 + " ��° ������!! "); //���  1S 2B F 
				    pw.flush();
				    
			    }
			    
		   }// while end
 
		} catch (IOException e) {
		   System.out.println("������ �ۼ��ſ���");
		   e.printStackTrace();
		} finally{
		   close();
		}
	} 
	
	
	public void close(){
		try {
		  // 4. ���� �ݱ� --> ���� ����
		  
		   if(br!=null)br.close();
		   if(pw!=null)pw.close();
		   if(os!=null)os.close();
		   if(is!=null)is.close();
		   if(socket!=null)socket.close();
		  }catch(IOException e) {
		   System.out.println("close����");
		   e.printStackTrace();
		  }
	}
}//������ Ŭ�� end  

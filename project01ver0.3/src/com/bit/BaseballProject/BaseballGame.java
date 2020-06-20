package com.bit.BaseballProject;


class BaseballGame{
	
	 public static int MAX_COUNT = 4;  // ���ڸ� ���ڷ� ������ �ϰڴ°�?
	 public static int MAX_INPUT = 5; // �ִ� ������� �����Է��� ���� ���ΰ�?
	 int x=0, y=0; //x= ��Ʈ����ũ ����, y=�� ����
	 private boolean val=false;
	 private int baseNumber[] = new int[MAX_COUNT]; //����迭
	 private int userNumber[] = new int[MAX_COUNT]; //����� �Է� �� �迭
	 private int cnt=0;
	 
	 BaseballGame(){}
	 
	 void randomInt(){ //�������� ���� �迭 �����
		 do{baseNumber[0] = (int)(Math.random()*10);
		 }while(baseNumber[0]==0);
	  
		 for(int i=1;i<baseNumber.length;i++){
			 baseNumber[i] = (int)(Math.random()*10);
			 for(int j=0;j<i;j++){
				 while(baseNumber[i]==baseNumber[j] || baseNumber[i]==0){
					 baseNumber[i] = (int)(Math.random()*10); 
					 j=0;
	     
				 }
			 }
		 }
		 for(int i=0;i<baseNumber.length;i++){
			 System.out.print(baseNumber[i]+" ");
		 }
	 }
	 
	 void inputUserNumber1(String in1){ // ����ڰ� �Է� �޾� ó��
	    
		 String in = in1;
		 for(int i=0;i<in.length();i++){
			 userNumber[i]=in.charAt(i)-48;
		 }
		 countSB(baseNumber,userNumber);
	  
 	}
	 
	 
	 
	synchronized void countSB(int a[],int b[]){ //��Ʈ����ũ �� ī��Ʈ
	  
		x=0; y=0; //��Ʈ����ũ, �� ���� �ʱ�ȭ
		for(int i=0;i<a.length;i++){
			for(int j=0;j<b.length;j++){
				if(b[i]==a[j])
					if(i==j) x+=1;
					else y+=1;
			}
		}
		cnt++;
		if(x==MAX_COUNT) {val=true; return;} //4��Ʈ����ũ�� ����
		if(cnt == MAX_INPUT){val=true; return;}
	}
	
	public int getX() { return x;}
	public int getY() { return y;}
	 
	int getCount(){ return cnt; } 
	boolean getValue(){ return val; } 
	 
	}
	
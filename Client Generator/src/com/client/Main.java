package com.client;

public class Main {
	
	public static void main(String[] args) {
		
		for(int i=0; i<100; i++) {
			Thread fireAndForget = new Thread(new FireAndForgetClient());
			fireAndForget.start();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				
			}
		}
		
	}
	
}

package com.server;

import java.io.IOException;

public class Main {

	static Thread connectionManagerThread;
	
	public static void main(String[] args) {		
		try {
			connectionManagerThread = new Thread(new ConnectionManager(10, 9090));
			connectionManagerThread.start();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Server online, all systems nominal");
		}
	}
	
}

package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class FireAndForgetClient implements Runnable {
	
	Socket connectionSocket;
	BufferedReader connectionReader;
	
	AtomicBoolean doneFlag = new AtomicBoolean(false);
	
	public FireAndForgetClient() {
		
	}

	@Override
	public void run() {
		
		initialize();
		
		while(!doneFlag.get()) {
			try {
				String data = connectionReader.readLine();
				if(data != null) {
					System.out.println(data);
				} else {
					doneFlag.set(true);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(doneFlag.get()) {
					try {
						connectionSocket.close();
						connectionReader.close();
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	private void initialize() {
		try {
			connectionSocket = new Socket("localhost", 9090);
			connectionReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("connection unavailable");
			doneFlag.set(true);
		} finally {
			
		}
	}

}

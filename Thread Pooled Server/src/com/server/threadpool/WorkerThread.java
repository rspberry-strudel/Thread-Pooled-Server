package com.server.threadpool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerThread extends Thread {
	
	Socket clientConnection = null;
	PrintWriter outputStream = null;	
	
	List<WorkerThread> availablePool;
	List<WorkerThread> workingPool;
	
	AtomicBoolean socketFlag = new AtomicBoolean(false);
	
	public WorkerThread(List<WorkerThread> availablePool, List<WorkerThread> workingPool) {
		this.availablePool = availablePool;
		this.workingPool = workingPool;
		availablePool.add(this);
	}
	
	public void setSocket(Socket clientConnection) {
		this.clientConnection = clientConnection;
		socketFlag.set(true);
		System.out.println("client connection delegated through setSocket() method");
	}

	@Override
	public void run() {
		
		while(true) {
			
			if(socketFlag.get()) {
				
				System.out.println("worker received socket, handling network transaction");
				
				availablePool.remove(this);
				workingPool.add(this);
				
				try {
					outputStream = new PrintWriter(clientConnection.getOutputStream(), true);
					outputStream.println("client has successfully connected");
					outputStream.println("data being sent");
					outputStream.println("connection closing");
					outputStream.close();
					clientConnection.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					clientConnection = null;
					outputStream = null;
					workingPool.remove(this);
					availablePool.add(this);
					socketFlag.set(false);
				}
				
			}
			
		}
		
	}

}

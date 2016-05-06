package com.server;

import java.io.IOException;
import java.net.ServerSocket;

import com.server.threadpool.ThreadPool;

public class ConnectionManager implements Runnable {
	
	ThreadPool pool;
	Thread threadPool;
	
	ServerSocket server;
	
	public ConnectionManager(int THREAD_COUNT, int port) throws IOException {
		server = new ServerSocket(port);
		pool = new ThreadPool(THREAD_COUNT);
		threadPool = new Thread(pool);
		threadPool.start();
		System.out.println("Connection Manager online and ready to handle connections");
	}

	@Override
	public void run() {
		while(true) {
			try {
				pool.giveTask(server.accept());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				
			}
		}
	}

}

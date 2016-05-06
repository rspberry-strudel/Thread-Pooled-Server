package com.server.threadpool;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ThreadPool implements Runnable {
	
	List<WorkerThread> availablePool = Collections.synchronizedList(new ArrayList<WorkerThread>());
	List<WorkerThread> workingPool = Collections.synchronizedList(new ArrayList<WorkerThread>());
	BlockingQueue<Socket> socketQueue = new LinkedBlockingDeque<Socket>();
	
	public ThreadPool(int THREAD_COUNT) {
		System.out.println("Spooling up worker threads");
		createPool(THREAD_COUNT);
		System.out.println("Threadpool initialized");
		}
	
	private void createPool(int threadcount) {
		for(int i=0; i < threadcount; i++) {
			WorkerThread worker = new WorkerThread(availablePool, workingPool);
			worker.start();
			System.out.println("worker thread number : " + i + " created");
		}
	}
	
	public void giveTask(Socket socket) {
		socketQueue.add(socket);
		System.out.println("socket connection added to data queue");
	}

	@Override
	public void run() {
		
		while(true) {
			
			if(!availablePool.isEmpty()) {
				try {
					availablePool.get(0).setSocket(socketQueue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					
				}
			}

		}
		
	}

}

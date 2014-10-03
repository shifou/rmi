package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import data.msgType;

public class Registry {
	public ConcurrentHashMap<String, Object> map;
	public ServerSocket listenSocket;
	public int requestId;
	private boolean running;

	public Registry(int registryPort) {
		try {
			listenSocket = new ServerSocket((short) registryPort);
			System.out.println("Registry start listen at: " + registryPort);
			running = true;
			requestId = 1;
		} catch (IOException e) {
			listenSocket = null;
			// System.out.println("failed to create the socket server");
		}
	}

	public void listen() {
		System.out.println("waiting for request");
		while (running) {
			Socket clientSocket;
			try {
				clientSocket = listenSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("socket server accept failed");
				continue;
			}
			System.out.println("Request " + requestId + " client: "
					+ clientSocket.getInetAddress() + ":"
					+ clientSocket.getPort() + " join in");
			RegistryService registryService;
			try {
				registryService = new RegistryService(requestId, clientSocket);
				new Thread(registryService).start();

			} catch (IOException e) {
				System.out.println("handler " + requestId + " failed");
				continue;
			}
			requestId++;
		}

		try {
			listenSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("socket Server failed to close");
		}
	}

	public void stop() {
		running = false;
	}

	public void addService(ArrayList<String> serviceNames) {
		// TODO Auto-generated method stub

	}

}
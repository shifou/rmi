package main;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import ror.*;
import data.msgType;

public class Registry {
	public ConcurrentHashMap<String, RemoteObjectRef> mp;
	public ConcurrentHashMap<String, Object> realmp;
	public ServerSocket listenSocket;
	public int requestId;
	private boolean running;

	public Registry(int registryPort) {
		try {
			listenSocket = new ServerSocket((short) registryPort);
			mp = new ConcurrentHashMap<String, RemoteObjectRef>();
			realmp = new ConcurrentHashMap<String, Object>();
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
		String []args=null;
		RemoteObjectRef p =null;
		String each=null;
		for(String hold: serviceNames){
		try {
			each= hold.split(" ")[0];
			Class<?> obj = Class.forName("application." + each);
			Constructor<?> objConstructor = obj.getConstructor(String[].class);
			p = (RemoteObjectRef) objConstructor
					.newInstance(new Object[] { args });
		} catch (ClassNotFoundException e) {
			System.out.println("no such class " + each);
			continue;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			continue;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			continue;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			continue;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			continue;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			continue;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			continue;
		}
		mp.put(each,p);
		realmp.put(each, p.localize());
		}
	}

}
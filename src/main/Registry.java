package main;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import ror.*;


public class Registry {

	public ConcurrentHashMap<String, RemoteObjectReference> mp;
	public ConcurrentHashMap<String, Object> realmp;
	public ServerSocket listenSocket;
	public String ipaddr;
	public int port;
	public int requestId;
	private boolean running;

	public Registry(int registryPort) {
		try {
			port=registryPort;
			listenSocket = new ServerSocket((short) registryPort);
			ipaddr=InetAddress.getLocalHost().getHostAddress();
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			mp = new ConcurrentHashMap<String, RemoteObjectReference>();
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
			if(clientSocket==null)
				continue;
			System.out.println("Request " + requestId + " handler in "
					+ clientSocket.getInetAddress() + ":"
					+ clientSocket.getPort() );
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
			
			e.printStackTrace();
			System.out.println("socket Server failed to close");
		}
	}

	public void stop() {
		running = false;
	}

	public void addServices(ArrayList<String> serviceNames) {
		RemoteObjectReference ror=null;
		Object p =null;
		String []line=null;
		for(String hold: serviceNames){
		try {
			line= hold.split(" ");
			String[] args = new String[line.length - 1];
			for (int i = 1; i < line.length; i++) {
				args[i - 1] = line[i];
			}
			Class<?> obj = Class.forName("application." + line[0]);
			Constructor<?> objConstructor = null;
			if(line.length>2){
				objConstructor = obj.getConstructor(String[].class);
				p = objConstructor.newInstance(new Object[]{args});
			}
				
			else {
				objConstructor = obj.getConstructor();
				p =  objConstructor.newInstance();
			}
				
			
		} catch (ClassNotFoundException e) {
			System.out.println("no such class " + line[0]);
			continue;
		} catch (SecurityException e) {
			
			e.printStackTrace();
			continue;
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
			continue;
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
			continue;
		} catch (InstantiationException e) {
			
			e.printStackTrace();
			continue;
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
			continue;
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
			continue;
		}
		realmp.put(line[1],p);
		ror =new RemoteObjectReference(this.ipaddr,port,line[0],line[1]);
		mp.put(line[1],ror);
		}
	}


}
package main;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import ror.*;
import data.Message;
import data.msgType;

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
			ipaddr=listenSocket.getInetAddress().getHostAddress();
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
	private void unbind(String name) {
		boolean check1=false;
		if(Server.reg.mp.containsKey(name)){
			Server.reg.mp.remove(name);
			check1=true;
		}
		if(check1)
		{
			System.out.println("unbind "+name+" from registry!");
		}
		else{
			System.out.println("unbind "+name+" from registry error!");
			}
	}
	public void rebind(String name, Object ob) {
		boolean check1=false;
		if(Server.reg.mp.containsKey(name)){
			Server.reg.realmp.put(name,ob);
			check1=true;
		}else{
			Server.reg.realmp.put(name,ob);
		}
		if(check1)
		{
			System.out.println("rebind "+name+" successfully!");
		}
		else{
			System.out.println(name+" does not exist, will bind!");
			ArrayList<String> hold= new ArrayList<String>();
			hold.add(name);
			bind(hold);
		}
	}
	public void bind(ArrayList<String> serviceNames) {
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
			Constructor<?> objConstructor = obj.getConstructor(String[].class);
			p =  objConstructor.newInstance(new Object[] { args });
		} catch (ClassNotFoundException e) {
			System.out.println("no such class " + line[0]);
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
		realmp.put(line[0],p);
		ror =new RemoteObjectReference(this.ipaddr,port,line[0],line[1]);
		mp.put(line[0],ror);
		}
	}

	public void bind(String ident,Object ob) {
		RemoteObjectReference ror= new RemoteObjectReference(this.ipaddr,port,ob.getClass().getName(),ident);
		realmp.put(ident,ob);
		mp.put(ident,ror);

		
	}

}
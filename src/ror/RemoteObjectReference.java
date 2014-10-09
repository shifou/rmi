package ror;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.net.InetAddress;



public class RemoteObjectReference implements Serializable  {

	private static final long serialVersionUID = -8158949034952976025L;
	private int serverPort;
	private String serverIP;
	private String class_name;
	private String ID;
	
	public RemoteObjectReference(String ipaddr, int port, String r_i_name, String ID) {
		
		this.serverPort = port;
		this.serverIP = ipaddr;
		this.class_name = r_i_name;
		this.ID = ID;
	}
	
	public Object localize() {
		
		String stubName = "application." + this.class_name + "_Stub";
		System.out.println(stubName);
		System.out.println(this.class_name);
		System.out.println(this.ID);
		try {
			Class<?> stub_class = Class.forName(stubName);
			Constructor<?> objConstructor = stub_class.getConstructor(String.class, int.class, String.class);
			Object stub = objConstructor.newInstance(serverIP, serverPort, ID);
			return stub;
			
		} catch (Exception e) {
			System.out.println("No stub associated with this ROR!");
			return null;
		}
		
	}

	public String getClassName() {
		return this.class_name;
	}
	
	public String getID(){
		return this.ID;
	}
	
}
package ror;

import java.io.Serializable;
import java.lang.reflect.Constructor;



public class RemoteObjectReference implements Serializable  {

	private static final long serialVersionUID = -8158949034952976025L;
	private int serverPort;
	private String serverIP;
	private String remote_interface_name;
	
	public RemoteObjectReference(String IP, int port, String r_i_name) {
		
		this.serverPort = port;
		this.serverIP = IP;
		this.remote_interface_name = r_i_name;
	}
	
	public Object localize() {
		
		String stubName = this.remote_interface_name + "_Stub";
		try {
			Class<?> stub_class = Class.forName(stubName);
			Constructor<?> objConstructor = stub_class.getConstructor(String.class, int.class);
			Object stub = objConstructor.newInstance(serverIP, serverPort);
			return stub;
			
		} catch (Exception e) {
			System.out.println("No stub associated with this ROR!");
			return null;
		}
		
	}

	public String getClassName() {
		return this.remote_interface_name;
	}
	
}
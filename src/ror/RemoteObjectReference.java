package ror;

import java.io.Serializable;
import java.lang.reflect.Constructor;



public class RemoteObjectReference implements Serializable  {

	private static final long serialVersionUID = -8158949034952976025L;
	private int serverPort;
	private String serverIP;
	private String remote_interface_name;
	private String ID;
	
	public RemoteObjectReference(String IP, int port, String r_i_name, String ID) {
		
		this.serverPort = port;
		this.serverIP = IP;
		this.remote_interface_name = r_i_name;
		this.ID = ID;
	}
	
	public Object localize() {
		
		String stubName = "application." + this.remote_interface_name + "_Stub";
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
		return this.remote_interface_name;
	}
	
	public String getID(){
		return this.ID;
	}
	
}
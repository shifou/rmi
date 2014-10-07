package ror;

import java.io.Serializable;



public class RemoteObjectReference implements Serializable  {

	private static final long serialVersionUID = -8158949034952976025L;
	private int port;
	private String host;
	private String remote_interface_name;
	
	public RemoteObjectReference(String host, int port, String r_i_name) {
		
		this.port = port;
		this.host = host;
		this.remote_interface_name = r_i_name;
	}
	
	public Object localize() {
		
		String stubName = this.remote_interface_name + "_stub";
		try {
			Class stub_class = Class.forName(stubName);
			Object stub = stub_class.newInstance();
			return stub;
			
		} catch (Exception e) {
			System.out.println("No stub associated with this ROR!");
			return null;
		}
		
		
	}
	
}
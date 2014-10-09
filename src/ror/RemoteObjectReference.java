package ror;

import java.io.Serializable;
import java.lang.reflect.Constructor;

/*
 * RemoteObjectReference is a class used to store a reference to the remote object. 
 * It is basically a class that holds information such as IP address of the remote
 * host, the port on which the remote host is accepting connections, the class name 
 * of the remote object and the ID by which the object is identified on the server/
 * remote host.
 * */

public class RemoteObjectReference implements Serializable, Remote440 {

	private static final long serialVersionUID = -8158949034952976025L;
	private int serverPort;
	private String serverIP;
	private String class_name;
	private String ID;

	public RemoteObjectReference(String ipaddr, int port, String r_i_name,
			String ID) {

		this.serverPort = port;
		this.serverIP = ipaddr;
		this.class_name = r_i_name;
		this.ID = ID;
	}

	/**
	 * The localize method is responsible for creating and returning a stub for
	 * the remote object to which this RemoteObjectReference refers.
	 * */
	public Object localize() throws Remote440Exception {

		/*
		 * stub names are the name of the implementing class followed by _Stub
		 */
		String stubName = this.class_name + "_Stub";
		System.out.println(stubName);
		try {
			Class<?> stub_class = Class.forName(stubName);
			Constructor<?> objConstructor = stub_class.getConstructor(
					String.class, int.class, String.class);
			Object stub = objConstructor.newInstance(serverIP, serverPort, ID);
			return stub;

		} catch (Exception e) {
			throw new Remote440Exception("No stub associated with this ROR!");
		}

	}

	public String getClassName() throws Remote440Exception {
		return this.class_name;
	}

	public String getID() throws Remote440Exception {
		return this.ID;
	}

}
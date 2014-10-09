package application;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ror.Remote440Exception;

public class ClientTrackerImpl_Stub implements ClientTracker {
	
	private int serverPort;
	private InetAddress serverIP;
	private ObjectInputStream serverIn;
	private ObjectOutputStream serverOut;
	private String identifier;
	
	public ClientTrackerImpl_Stub(String IP, int port, String identifier){
		this.serverPort = port;
		this.identifier = identifier;
		try {
			this.serverIP = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		}
	}
	
	@Override
	public void login() throws Remote440Exception {
		

	}

	@Override
	public void logout() throws Remote440Exception {
		

	}

	@Override
	public int getTotalVisited() throws Remote440Exception {
		
		return 0;
	}

	@Override
	public void setVisitorName(String name) throws Remote440Exception {
		

	}

	@Override
	public String getLastVisitorName() throws Remote440Exception {
		
		return null;
	}

	@Override
	public int getCurrentVisitors() throws Remote440Exception {
		
		return 0;
	}

}

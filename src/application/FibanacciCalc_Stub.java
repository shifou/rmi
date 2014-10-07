package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ror.Remote440Exception;

public class FibanacciCalc_Stub implements FibonacciCalc {

	private int serverPort;
	private InetAddress serverIP;
	private ObjectInputStream serverIn;
	private ObjectOutputStream serverOut;
	
	public FibanacciCalc_Stub(String IP, int port){
		this.serverPort = port;
		try {
			this.serverIP = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		}
	}
	
	@Override
	public int nthFibonacci(int n) throws Remote440Exception {
		int result = -1;
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			
		} catch (IOException e) {
			throw new Remote440Exception("Failed!");
		}
		return result;
	}

}

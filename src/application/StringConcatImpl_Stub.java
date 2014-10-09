package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import data.Message;
import data.msgType;
import ror.Remote440Exception;

public class StringConcatImpl_Stub implements StringConcat {



	private int serverPort;
	private InetAddress serverIP;
	private ObjectInputStream serverIn;
	private ObjectOutputStream serverOut;
	private String identifier;
	
	public StringConcatImpl_Stub(String IP, int port, String identifier){
		this.serverPort = port;
		this.identifier = identifier;
		try {
			this.serverIP = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		}
	}
	
	@Override
	public String concat(String a, String b) throws Remote440Exception {
		String result = "";
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			Object[] args = new Object[2];
			args[0] = a; 
			args[1] = b;
			Message message = new Message(msgType.INVOKE,args, new String("concat"), new String(this.identifier));
			this.serverOut.writeObject(message);
			this.serverOut.flush();
			
			Message recvMessage = (Message)(this.serverIn.readObject());
			toServer.close();
			if (recvMessage.getResponType() == msgType.INVOKEERROR){
				throw new Remote440Exception("failed!");
			}
			else {
				result = (String)(recvMessage.getReturnVal());
			}
			
			
			
		} catch (IOException e) {
			throw new Remote440Exception("Failed!");
		}
		catch (ClassNotFoundException e){
			throw new Remote440Exception("Failed!");
		}
		return result;
	}
	


}

package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import data.Message;
import data.msgType;
import ror.Remote440;
import ror.Remote440Exception;

/**
 * PersonImpl_Stub is the stub for PersonImpl application of the interface. So
 * when create is invoked, the stub connects with the server where PersonImpl is
 * hosted, sends the server a message to invoke create with appropriate
 * arguments, reads the message reply from the server, determines whether the
 * invocation was successful and if it was, returns the result by reading it
 * from the server's message
 * */
public class PersonImpl_Stub implements Person {

	private int serverPort;
	private InetAddress serverIP;
	private ObjectInputStream serverIn;
	private ObjectOutputStream serverOut;
	private String identifier;

	public PersonImpl_Stub(String IP, int port, String identifier) {
		this.serverPort = port;
		this.identifier = identifier;
		try {
			this.serverIP = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();

		}
	}

	@Override
	public String create(Remote440 a, Remote440 b, String first, String second,
			Integer n) throws Remote440Exception {
		String result = "";
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			Object[] args = new Object[5];
			args[0] = a;
			args[1] = b;
			args[2] = first;
			args[3] = second;
			args[4] = n;
			Message message = new Message(msgType.INVOKE, args, new String(
					"create"), new String(this.identifier));
			this.serverOut.writeObject(message);
			this.serverOut.flush();

			Message recvMessage = (Message) (this.serverIn.readObject());
			toServer.close();
			if (recvMessage.getResponType() == msgType.INVOKEERROR) {
				throw new Remote440Exception("failed!");
			} else {
				result = (String) (recvMessage.getReturnVal());
			}

		} catch (IOException e) {
			throw new Remote440Exception("Failed!");
		} catch (ClassNotFoundException e) {
			throw new Remote440Exception("Failed!");
		}
		return result;
	}

}

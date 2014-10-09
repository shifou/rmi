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

/**
 * FibonacciCalcImpl_Stub is the stub for FibonacciCalcImpl application of the
 * interface. So when nthFibonacci is invoked, the stub connects with the server
 * where FibonacciCalcImpl is hosted, sends the server a message to invoke
 * nthFibonacci with argument n, reads the message reply from the server,
 * determines whether the invocation was successful and if it was, returns the
 * result by reading it from the server's message
 * */
public class FibonacciCalcImpl_Stub implements FibonacciCalc {

	private int serverPort;
	private InetAddress serverIP;
	private ObjectInputStream serverIn;
	private ObjectOutputStream serverOut;
	private String identifier;

	public FibonacciCalcImpl_Stub(String IP, int port, String identifier) {
		this.serverPort = port;
		this.identifier = identifier;
		try {
			this.serverIP = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();

		}
	}

	@Override
	public int nthFibonacci(Integer n) throws Remote440Exception {
		int result = -1;
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			Object[] args = new Object[1];
			args[0] = n;
			Message message = new Message(msgType.INVOKE, args, new String(
					"nthFibonacci"), new String(this.identifier));
			this.serverOut.writeObject(message);
			this.serverOut.flush();

			Message recvMessage = (Message) (this.serverIn.readObject());
			toServer.close();
			if (recvMessage.getResponType() == msgType.INVOKEERROR) {
				throw new Remote440Exception("failed!");
			} else {
				result = (Integer) (recvMessage.getReturnVal());
			}

		} catch (IOException e) {
			throw new Remote440Exception("Failed!");
		} catch (ClassNotFoundException e) {
			throw new Remote440Exception("Failed!");
		}
		return result;
	}

}

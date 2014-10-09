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
 * TrackerImpl_Stub is the stub for TrackerImpl application of the interface. So
 * when a method is invoked, the stub connects with the server where TrackerImpl
 * is hosted, sends the server a message to invoke the method in question with
 * appropriate arguments, reads the message reply from the server, determines
 * whether the invocation was successful and if it was, returns the result by
 * reading it from the server's message
 * */
public class TrackerImpl_Stub implements Tracker {

	private int serverPort;
	private InetAddress serverIP;
	private ObjectInputStream serverIn;
	private ObjectOutputStream serverOut;
	private String identifier;

	public TrackerImpl_Stub(String IP, int port, String identifier) {
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
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			Message message = new Message(msgType.INVOKE, null, new String(
					"login"), new String(this.identifier));
			this.serverOut.writeObject(message);
			this.serverOut.flush();

			Message recvMessage = (Message) (this.serverIn.readObject());
			toServer.close();
			if (recvMessage.getResponType() == msgType.INVOKEERROR) {
				throw new Remote440Exception("failed!");
			}

		} catch (IOException e) {
			throw new Remote440Exception("Failed!");
		} catch (ClassNotFoundException e) {
			throw new Remote440Exception("Failed!");
		}

	}

	@Override
	public void logout() throws Remote440Exception {
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			Message message = new Message(msgType.INVOKE, null, new String(
					"logout"), new String(this.identifier));
			this.serverOut.writeObject(message);
			this.serverOut.flush();

			Message recvMessage = (Message) (this.serverIn.readObject());
			toServer.close();
			if (recvMessage.getResponType() == msgType.INVOKEERROR) {
				throw new Remote440Exception("failed!");
			}

		} catch (IOException e) {
			throw new Remote440Exception("Failed!");
		} catch (ClassNotFoundException e) {
			throw new Remote440Exception("Failed!");
		}

	}

	@Override
	public int getTotalVisited() throws Remote440Exception {
		int result = -1;
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			Message message = new Message(msgType.INVOKE, null, new String(
					"getTotalVisited"), new String(this.identifier));
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

	@Override
	public void setVisitorName(String name) throws Remote440Exception {
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			Object[] args = new Object[1];
			args[0] = name;
			Message message = new Message(msgType.INVOKE, args, new String(
					"setVisitorName"), new String(this.identifier));
			this.serverOut.writeObject(message);
			this.serverOut.flush();

			Message recvMessage = (Message) (this.serverIn.readObject());
			toServer.close();
			if (recvMessage.getResponType() == msgType.INVOKEERROR) {
				throw new Remote440Exception("failed!");
			}

		} catch (IOException e) {
			throw new Remote440Exception("Failed!");
		} catch (ClassNotFoundException e) {
			throw new Remote440Exception("Failed!");
		}

	}

	@Override
	public String getLastVisitorName() throws Remote440Exception {
		String result = "";
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			Message message = new Message(msgType.INVOKE, null, new String(
					"getLastVisitorName"), new String(this.identifier));
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

	@Override
	public int getCurrentVisitors() throws Remote440Exception {
		int result = -1;
		try {
			Socket toServer = new Socket(this.serverIP, this.serverPort);
			this.serverOut = new ObjectOutputStream(toServer.getOutputStream());
			this.serverOut.flush();

			this.serverIn = new ObjectInputStream(toServer.getInputStream());
			Message message = new Message(msgType.INVOKE, null, new String(
					"getCurrentVisitors"), new String(this.identifier));
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

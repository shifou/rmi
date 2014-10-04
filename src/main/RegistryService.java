package main;



import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import data.Message;
import data.msgType;


public class RegistryService implements Runnable {
	private Socket socket;
	private volatile boolean running;
	private int id;
	private ObjectInputStream objInput;
	private ObjectOutputStream objOutput;
	public RegistryService(int requestId, Socket clientSocket) throws IOException {
		id = requestId;
		socket = clientSocket;
		objOutput = new ObjectOutputStream(clientSocket.getOutputStream());
		objOutput.flush();
		objInput = new ObjectInputStream(clientSocket.getInputStream());
		running=true;
	}

	@Override
	public void run() {
			Message receiveMessage=null;
			while (running) {
				try {

					receiveMessage = (Message) objInput.readObject();
					
				} catch (ClassNotFoundException e) {
					//System.out.println("read disconnected message");
					continue;
				}
				catch(EOFException e)
				{
					//System.out.println("detect disconnected message");
					running=false;
				}
				catch(Exception e)
				{
					//System.out.println("-----");
					continue;
				}
				switch (receiveMessage.getResponType()) {
				case LOOKUP:
					handleLOOKUP(receiveMessage);
					break;
				case REBIND:
					handleREBIND(receiveMessage);
					break;
				case LIST:
					handleLIST(receiveMessage);
					break;
				case UNBIND:
					handleUNBIND(receiveMessage);
					break;
				case INVOKE:
					handleINVOKE(receiveMessage);
					break;
				}
			}

		}
	private void handleINVOKE(Message receiveMessage) {
		// TODO Auto-generated method stub
		
	}

	public int send(Message mes) throws IOException {
		try
		{
			

				objOutput.writeObject(mes);
				objOutput.flush();
			
		}catch(Exception e)
		{
			return 0;
		}
		return 1;
	}

	private void handleUNBIND(Message receiveMessage) {
		// TODO Auto-generated method stub
		
	}

	private void handleLIST(Message receiveMessage) {
		// TODO Auto-generated method stub
		
	}

	private void handleREBIND(Message receiveMessage) {
		// TODO Auto-generated method stub
		
	}

	private void handleLOOKUP(Message receiveMessage) {
		// TODO Auto-generated method stub
		
	}
}
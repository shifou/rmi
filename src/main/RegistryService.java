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
		try {
			Message receiveMessage;
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
					continue;
				}
				catch(Exception e)
				{
					//System.out.println("-----");
					continue;
				}
				switch (receiveMessage.getResponType()) {
				
				}
	}
		}catch(Exception e){
			
		}
	}
}
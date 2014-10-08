package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ror.Remote440Exception;
import ror.RemoteObjectReference;
import data.Message;
import data.msgType;

public class FibonacciClient {

	public static void main(String[] args){
		try {
			int serverPort = Integer.parseInt(args[0]);
			InetAddress serverIP = InetAddress.getByName(args[1]);
			Socket toServer = new Socket(serverIP, serverPort);
			ObjectOutputStream serverOut = new ObjectOutputStream(toServer.getOutputStream());
			serverOut.flush();
			ObjectInputStream serverIn = new ObjectInputStream(toServer.getInputStream());
			Message lookupMsg = new Message(msgType.LOOKUP, "FibonacciCalcImpl");
			serverOut.writeObject(lookupMsg);
			serverOut.flush();
			Message recvMessage = (Message)serverIn.readObject();
			toServer.close();
			RemoteObjectReference ror = recvMessage.getROR();
			FibonacciCalc fib = (FibonacciCalc)ror.localize();
			System.out.println(fib.nthFibonacci(5));
		}
		catch (UnknownHostException e){
			
		}
		catch (IOException e){
			
		}
		catch (ClassNotFoundException e){
			
		} catch (Remote440Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

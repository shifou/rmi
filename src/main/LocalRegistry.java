package main;
import data.Message;

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
import ror.RemoteObjectReference;

public class LocalRegistry {
  private InetAddress ip;
  private int port;

  public LocalRegistry(String ip, int port) {
    try {
		this.ip = InetAddress.getByName(ip);
		this.port = port;
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}
    
  }

  public boolean rebind(String serviceName, Object ob) throws IOException{

      Message msg = new Message(serviceName,ob,msgType.REBIND);
      Socket socket=null;
	try {
		socket = new Socket(this.ip, this.port);
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
      ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
      objOutput.writeObject(msg);
      objOutput.flush();
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      Message rep=null;
	try {
		rep = (Message)in.readObject();
	} catch (ClassNotFoundException e) {
		
		e.printStackTrace();
	}
      objOutput.close();
      in.close();
      socket.close();
      if (rep.getResponType()==msgType.REBINDOK) {
    	  return true;
      }
      else{
    	  System.out.println(rep.getMeg());
    	  return false;
      }
}

  public RemoteObjectReference lookup(String serviceName) throws Exception {
    try {
      Message msg = new Message( serviceName,msgType.LOOKUP);
      Socket clientSocket = new Socket(this.ip, this.port);
      ObjectOutputStream objOutput = new ObjectOutputStream(clientSocket.getOutputStream());
      objOutput.writeObject(msg);
      objOutput.flush();
      ObjectInputStream	objInput = new ObjectInputStream(clientSocket.getInputStream());
      Message rep = (Message) objInput.readObject();
      objInput.close();
      objOutput.close();
      clientSocket.close();
      if (rep.getResponType()==msgType.LOOKUPOK) {
        RemoteObjectReference ror = (RemoteObjectReference)rep.getReturnVal();
        return ror;
      } else {
        throw new Remote440Exception("no such service!");
      }
    } catch (Exception e) {
      throw new Remote440Exception(e.getMessage());
    }
  }
}
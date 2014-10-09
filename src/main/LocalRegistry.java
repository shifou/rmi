package main;
import data.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import data.msgType;
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

  
  public String list() throws IOException{
	  Message msg = new Message( msgType.LIST);
      Socket clientSocket = new Socket(this.ip, this.port);
      ObjectOutputStream objOutput = new ObjectOutputStream(clientSocket.getOutputStream());
      objOutput.writeObject(msg);
      objOutput.flush();
      ObjectInputStream	objInput = new ObjectInputStream(clientSocket.getInputStream());
      Message rep=null;
	try {
		rep = (Message) objInput.readObject();
	} catch (ClassNotFoundException e) {

		e.printStackTrace();
	}
      objInput.close();
      objOutput.close();
      clientSocket.close();
      return rep.getMeg();
     
  }
  public RemoteObjectReference lookup(String serviceName) throws Exception {
    try {
      Message msg = new Message(msgType.LOOKUP, serviceName);
      Socket clientSocket = new Socket(this.ip, this.port);
      ObjectOutputStream objOutput = new ObjectOutputStream(clientSocket.getOutputStream());
      objOutput.flush();
      objOutput.writeObject(msg);
      objOutput.flush();
      ObjectInputStream	objInput = new ObjectInputStream(clientSocket.getInputStream());
      Message rep = (Message) objInput.readObject();
      objInput.close();
      objOutput.close();
      clientSocket.close();
      if (rep.getResponType()==msgType.LOOKUPOK) {
        RemoteObjectReference ror = rep.getROR();
        return ror;
      } else {
        throw new Remote440Exception("no such service!");
      }
    } catch (Exception e) {
      throw new Remote440Exception(e.getMessage());
    }
  }
  public String rebind(String serviceName, Object ob) throws IOException{

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
      return rep.getMeg();
    
}
public String unbind(String serviceName) throws IOException {
	  Message msg = new Message(msgType.UNBIND,serviceName);
      Socket socket=null;
	try {
		socket = new Socket(this.ip, this.port);
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
      ObjectOutputStream objOutput=null;
      ObjectInputStream in=null;
      try {
		objOutput = new ObjectOutputStream(socket.getOutputStream());
	
      objOutput.writeObject(msg);
      objOutput.flush();
      in = new ObjectInputStream(socket.getInputStream());
      } 
      catch (IOException e1) {
  		// TODO Auto-generated catch block
  		e1.printStackTrace();
  	}
      Message rep=null;
	try {
		rep = (Message)in.readObject();
	} catch (ClassNotFoundException e) {
		
		e.printStackTrace();
	}
      objOutput.close();
      in.close();
      socket.close();
      return rep.getMeg();
}

public String bind(String string, Object ob) throws IOException {
	 Message msg = new Message(ob,msgType.BIND);
     Socket socket=null;
	try {
		socket = new Socket(this.ip, this.port);
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
     ObjectOutputStream objOutput=null;
     ObjectInputStream in=null;
     try {
		objOutput = new ObjectOutputStream(socket.getOutputStream());
	
     objOutput.writeObject(msg);
     objOutput.flush();
     in = new ObjectInputStream(socket.getInputStream());
     } 
     catch (IOException e1) {
 		// TODO Auto-generated catch block
 		e1.printStackTrace();
 	}
     Message rep=null;
	try {
		rep = (Message)in.readObject();
	} catch (ClassNotFoundException e) {
		
		e.printStackTrace();
	}
     objOutput.close();
     in.close();
     socket.close();
     return rep.getMeg();
}
}
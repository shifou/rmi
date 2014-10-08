package main;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import data.Message;
import data.msgType;
import ror.Remote440;
import ror.Remote440Exception;
import ror.RemoteObjectReference;

public class RegistryClient {
  private String ip;
  private int port;

  public RegistryClient(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  
  public Remote440 lookup(String serviceName) throws Exception {
    try {
      Message msg = new Message( serviceName,msgType.LOOKUP);
      Socket clientSocket = new Socket(this.ip, this.port);
      ObjectOutputStream objOutput = new ObjectOutputStream(clientSocket.getOutputStream());
      objOutput.flush();
      ObjectInputStream	objInput = new ObjectInputStream(clientSocket.getInputStream());
      Message rep = (Message) objInput.readObject();
      objInput.close();
      objOutput.close();
      clientSocket.close();
      if (rep.getResponType()==msgType.LOOKUPOK) {
        RemoteObjectReference ror = (RemoteObjectReference)rep.getReturnVal();
        return (Remote440)ror.localize();
      } else {
        throw new Remote440Exception("no such service!");
      }
    } catch (Exception e) {
      throw new Remote440Exception(e.getMessage());
    }
  }
}
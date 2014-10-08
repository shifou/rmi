package main;



import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import ror.RemoteObjectReference;
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
				case LIST:
					handleLIST(receiveMessage);
					break;
				case INVOKE:
					handleINVOKE(receiveMessage);
					break;
				default:
					System.out.println("receive messgae error");
					continue;
				}
			}

		}
	private void handleINVOKE(Message receiveMessage) {
		String name= receiveMessage.getName();
		String methodName = receiveMessage.getMethodName();
		Object realObj= Server.reg.realmp.get(name);
        Object[] args = receiveMessage.getArg();
        Method method = null;
        if (args != null) {
          Class[] types = new Class[args.length];
          for (int i = 0; i < types.length; i++) {
            if (args[i] instanceof RemoteObjectReference) { // Argument is RemoteObjectReference of Remote440 object
              Class className = Class.forName(((RemoteObjectReference)args[i]).getClassName());
              types[i] = (className.getInterfaces())[0];  // Get interface class
              args[i] = ((RemoteObjectReference)args[i]).localize();  // Get stub from RemoteObjectReference
            } else {  // Argument is Serializable object
              types[i] = args[i].getClass();
            }
          }
          method = realObj.getClass().getMethod(methodName, types);
        } else {  // Argument is null
          method = realObj.getClass().getMethod(methodName, (Class[])null);
        }
        Object returnVal = method.invoke(realObj, args);

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
	private void handleLIST(Message receiveMessage) {
		String ans="";
		int i=1;
		for(String each: Server.reg.mp.keySet()){
			ans+=(i+":\t"+each+"\n");
		}
		if(ans.equals(""))
			ans="no service available right now!";
		Message mes= new Message(ans,msgType.REPLY);
		try {
			send(mes);
		} catch (IOException e) {
			System.out.println("send list error");
			e.printStackTrace();
		}
	}
	private void handleLOOKUP(Message receiveMessage) {
		// TODO Auto-generated method stub
		String name= receiveMessage.getName();
		String ans="no such service!";
		Message mes=null;
		if(Server.reg.mp.containsKey(name)){
			mes= new Message(Server.reg.mp.get(name),msgType.PASSREF);
		}else{
			if(Server.reg.realmp.containsKey(name)){
			mes= new Message(Server.reg.realmp.get(name),msgType.PASSVAL);
			}else{
				mes= new Message(ans,msgType.LOOKUPFAIL);
			}
		}
		try {
			send(mes);
		} catch (IOException e) {
			System.out.println("send lookup error");
			e.printStackTrace();
		}
	}
}
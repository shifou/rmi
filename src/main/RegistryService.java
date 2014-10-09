package main;

import java.io.EOFException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import ror.Remote440;
import ror.RemoteObjectReference;
import data.Message;
import data.msgType;

public class RegistryService implements Runnable {
	private Socket socket;
	private volatile boolean running;
	private int id;
	private ObjectInputStream objInput;
	private ObjectOutputStream objOutput;

	public RegistryService(int requestId, Socket clientSocket)
			throws IOException {
		id = requestId;
		socket = clientSocket;
		objOutput = new ObjectOutputStream(clientSocket.getOutputStream());
		objOutput.flush();
		objInput = new ObjectInputStream(clientSocket.getInputStream());
		running = true;
	}

	@Override
	public void run() {
		Message receiveMessage = null;
		// keep running 
		while (running) {
			try {
				receiveMessage = (Message) objInput.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("request: "+this.id+" closed");
				running = false;
				break;
			} catch (EOFException e) {
				System.out.println("request: "+this.id+" eof closed");
				running = false;
				break;
			} catch (Exception e) {
				System.out.println("request: "+this.id+" closed");
				running = false;
				break;
			}
			// handle depend on the msgType
			switch (receiveMessage.getResponType()) {
			case UNBIND:
				handleUNBIND(receiveMessage);
				break;
			case REBIND:
				handleREBIND(receiveMessage);
				break;
			case BIND:
				handleBIND(receiveMessage);
				break;
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
				System.out.println("receive messgae type error");
				continue;
			}
		}
		try {
			objInput.close();
			objOutput.close();
			socket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	    
	}
	
	public void handleUNBIND(Message receiveMessage) {
		// get the servicename we want to unbind
		String name = receiveMessage.getObjectID();
		boolean check1=false;
		//check whether this servicename exist in the registry right now
		if(Server.reg.mp.containsKey(name)){
			Server.reg.mp.remove(name);
			Server.reg.realmp.remove(name);
			check1=true;
		}
		String ans="";
		Message mes=null;
		// if exist
		if(check1)
		{
			ans="unbind "+name+" from registry!";
			mes = new Message(ans,msgType.UNBINDOK);
		}
		//if not
		else{
			ans="no such service unbind "+name+" from registry error!";
			mes=new Message(ans,msgType.UNBINDERROR);
		}
		try {
			send(mes);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println(ans);
	}
	
	public void handleBIND(Message receiveMessage) {
		// get the servicename and object we want to bind
		String ident=receiveMessage.getObjectID();
		Object ob =receiveMessage.getReturnVal();
		String ans="";
		Message mes=null;
		//generate the ror
		RemoteObjectReference ror= new RemoteObjectReference(Server.reg.ipaddr,Server.reg.port,ob.getClass().getName(),ident);
		// if the service already exist we return error
		if(Server.reg.realmp.containsKey(ident)){
			ans="already has this service, try using rebind";
			mes = new Message(ans,msgType.BINDERROR);
		}
		else{
		// if not exist we bind
		Server.reg.realmp.put(ident,ob);
		Server.reg.mp.put(ident,ror);
		ans="bind service successfully!";
		mes = new Message(ans,msgType.BINDOK);
		}
		try {
			send(mes);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println(ans);
	}
	public void handleREBIND(Message receiveMessage) {
		// get the servicename and object we want to bind
		String name=receiveMessage.getObjectID();
		Object ob =receiveMessage.getReturnVal();
		String ans="";
		Message mes=null;
		//generate ror
		RemoteObjectReference ror= new RemoteObjectReference(Server.reg.ipaddr,Server.reg.port,ob.getClass().getName(),name);
		// if service exist we rebind
		if(Server.reg.mp.containsKey(name)){
			ans="rebind successfully!";
			mes = new Message(ans,msgType.REBINDOK);
		}
		// if not we should bind the service
		else{
			ans="service not exist, bind this service!";
			mes = new Message(ans,msgType.REBINDINFO);
		}
		// both cases we are doing the same way
		Server.reg.realmp.put(name,ob);
		Server.reg.mp.put(name,ror);
		try {
			send(mes);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println(ans);
	}
	private void handleINVOKE(Message receiveMessage) {
		// get the servicename, methodname, args[] object array and the really object for invoking
		String name = receiveMessage.getObjectID();
		String methodName = receiveMessage.getMethodName();
		Object realObj = Server.reg.realmp.get(name);
		Object[] args = receiveMessage.getArg();
		Method method = null;
		String msg=null;
		Message mes = null;
		boolean run=true;
		// if agrs not null 
		if (args != null) {
			Class[] types = new Class[args.length];
			for (int i = 0; i < types.length; i++) {
				// if this args[i] is null or if this args[i] is not serializable we berak 
				if(args[i]==null || !(args[i] instanceof Serializable)){
					System.out.println("no serializable args or args is null");
					msg="no serializable args or args is null";
					run=false;
					break;
				}
				// if args[i] is a ror actually we get the Remote440 type always
				if (args[i] instanceof Remote440) {
					
					types[i] = Remote440.class;
					
					String id=((RemoteObjectReference) args[i]).getID();
					if(Server.reg.realmp.containsKey(id))
					{
						args[i] =  Server.reg.realmp.get(id);
					}
					else{
						System.out.println("can not find the ror object in the server");
						msg="ror service object not available right now";
						run=false;
						break;
					}
				} else {
					types[i] = args[i].getClass();
				}
				//System.out.println(types[i]+"---");
			}
			if(run==false){
				 mes = new Message(msg,msgType.INVOKEERROR);
				 try {
						send(mes);
					} catch (IOException e) {
						
						e.printStackTrace();
					
					}
				 return;
			}
			
			try {
				// construct method with args
				method = realObj.getClass().getMethod(methodName, types);
			} catch (NoSuchMethodException e) {
				
				run=false;
				msg=e.getMessage();
				e.printStackTrace();
			} catch (SecurityException e) {
				
				run=false;
				msg=e.getMessage();
				e.printStackTrace();
			}
		} 
		else { 
			// null Argument
			try {
				//construct method without args
				method = realObj.getClass().getMethod(methodName,
						(Class[]) null);
			} catch (NoSuchMethodException e) {
				run=false;
				msg=e.getMessage();
				e.printStackTrace();
			} catch (SecurityException e) {
				
				run=false;
				msg=e.getMessage();
				e.printStackTrace();
			}
		}
		Object returnVal=null;
		try {
			//trying invoke and catch exception
			returnVal = method.invoke(realObj, args);
		} catch (IllegalAccessException e) {
			run=false;
			msg=e.getMessage();
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			run=false;
			msg=e.getMessage();
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			run=false;
			msg=e.getMessage();
			e.printStackTrace();
		}
		Object ans=null;
		try {
			// return val should be Serializable
		ans= checkRet(returnVal);
       }catch(Exception e){
			run=false;
			msg=e.getMessage();
			e.printStackTrace();
		}
		 
		if(run){
			 mes = new Message(ans,msgType.INVOKEOK);
		}
		else{
			 mes = new Message(msg,msgType.INVOKEERROR);
		}
		try {
			send(mes);
		} catch (IOException e) {
			e.printStackTrace();
		}
}

	private Object checkRet(Object obj) throws NotSerializableException {
		Object ret=obj;
		if ( (obj != null) && !(obj instanceof Serializable) ) {
			throw new NotSerializableException("Non-serializable return object");
		}
		return ret;	
	}
	// send message
	public int send(Message mes) throws IOException {
		try {
			objOutput.writeObject(mes);
			objOutput.flush();
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	private void handleLIST(Message receiveMessage) {
		// print the current service name 
		String ans = "";
		int i = 1;
		for (String each : Server.reg.mp.keySet()) {
			ans += (i + ":\t" + each + "\n");
			i++;
		}
		if (ans.equals(""))
			ans = "no service available right now!";
		Message mes = new Message(ans, msgType.REPLY);
		try {
			send(mes);
		} catch (IOException e) {
			System.out.println("send list error");
			e.printStackTrace();
		}
	}

	private void handleLOOKUP(Message receiveMessage) {
		// get the servicename
		String name = receiveMessage.getObjectID();
		String ans = "no such service! ";
		Message mes = null;
		// if exist
		if (Server.reg.mp.containsKey(name)) {
			mes = new Message(Server.reg.mp.get(name), msgType.LOOKUPOK);
		} else {
			// else
			ans = "no corresponding ROR found!";
			mes = new Message(ans, msgType.LOOKUPERROR);
		}
		try {
			send(mes);
		} catch (IOException e) {
			System.out.println("send lookup error");
			e.printStackTrace();
		}
	}
}
package main;

import java.io.EOFException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
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
		while (running) {
			try {
				receiveMessage = (Message) objInput.readObject();
			} catch (ClassNotFoundException e) {
				// System.out.println("read disconnected message");
				continue;
			} catch (EOFException e) {
				// System.out.println("detect disconnected message");
				running = false;
			} catch (Exception e) {
				// System.out.println("-----");
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
		String name = receiveMessage.getName();
		String methodName = receiveMessage.getMethodName();
		Object realObj = Server.reg.realmp.get(name);
		Object[] args = receiveMessage.getArg();
		Method method = null;
		String msg = null;
		boolean run = true;
		if (args != null) {
			Class[] types = new Class[args.length];
			for (int i = 0; i < types.length; i++) {
				if (args[i] instanceof RemoteObjectReference) {
					Class className = null;
					try {
						className = Class
								.forName(((RemoteObjectReference) args[i])
										.getClassName());
					} catch (ClassNotFoundException e) {
						run = false;
						msg = e.getMessage();
						System.out.println("not found the class");
						e.printStackTrace();
					}
					types[i] = (className.getInterfaces())[0];
					args[i] = ((RemoteObjectReference) args[i]).localize();
				} else {
					types[i] = args[i].getClass();
				}
			}
			try {
				method = realObj.getClass().getMethod(methodName, types);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				run = false;
				msg = e.getMessage();
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				run = false;
				msg = e.getMessage();
				e.printStackTrace();
			}
		} else {
			// null Argument
			try {
				method = realObj.getClass().getMethod(methodName,
						(Class[]) null);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				run = false;
				msg = e.getMessage();
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				run = false;
				msg = e.getMessage();
				e.printStackTrace();
			}
		}
		Object returnVal = null;
		try {
			returnVal = method.invoke(realObj, args);
		} catch (IllegalAccessException e) {
			run = false;
			msg = e.getMessage();
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			run = false;
			msg = e.getMessage();
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			run = false;
			msg = e.getMessage();
			e.printStackTrace();
		}
		Object ans = null;
		try {
			ans = checkRet(returnVal);
		} catch (Exception e) {

		}
		Message mes = null;
		if (run) {
			mes = new Message(ans, msgType.INVOKEOK);
		} else {
			mes = new Message(msg, msgType.INVOKEERROR);
		}
		try {
			send(mes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Object checkRet(Object returnVal) throws NotSerializableException {
		// TODO Auto-generated method stub
		return null;
	}

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
		String ans = "";
		int i = 1;
		for (String each : Server.reg.mp.keySet()) {
			ans += (i + ":\t" + each + "\n");
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
		// TODO Auto-generated method stub
		String name = receiveMessage.getName();
		String ans = "no such service!";
		Message mes = null;
		if (Server.reg.mp.containsKey(name)) {
			mes = new Message(Server.reg.mp.get(name), msgType.LOOKUPOK);
		} else {
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
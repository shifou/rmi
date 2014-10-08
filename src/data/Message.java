package data;
import java.io.Serializable;

import ror.RemoteObjectReference;



public class Message implements Serializable {


	private static final long serialVersionUID = -3134382594687183495L;
	
	RemoteObjectReference ref;
	String serviceName;
	msgType type;
	Object[] methodArgs;
	Object realob;
	String methodName; 
	String reply;
	Object returnVal;
	
	
	public Message(String ans, msgType type) {
		reply= ans;
		this.type = type;
	}
	
	public Message(msgType type, Object[] args, String name){
		this.methodArgs = args;
		this.type = type;
		this.methodName = name;
	}
	
	public Message(RemoteObjectReference ror, msgType passref) {
		type=passref;
		ref=ror;
		
	}

	public Message(Object object, msgType passval) {
		type=passval;
		realob=object;
	}

	public msgType getResponType() {
	
		return this.type;
	}
	public String getMethodName() {
	
		return methodName;
	}

	public String getName() {
		return serviceName;
	}

	public Object[] getArg() {
		// TODO Auto-generated method stub
		return methodArgs;
	}
	
}
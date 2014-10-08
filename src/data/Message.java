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
	String objectID;
	
	
	public Message(String ans, msgType type) {
		reply= ans;
		this.type = type;
	}
	
	public Message(msgType type, Object[] args, String name, String objectID){
		this.methodArgs = args;
		this.type = type;
		this.methodName = name;
		this.objectID = objectID;
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
		
		return methodArgs;
	}
	
	public Object getReturnVal(){
		return this.returnVal;
	}
	
	public String getObjectID(){
		return this.objectID;
	}
	
}
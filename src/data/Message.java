package data;
import java.io.Serializable;

import ror.RemoteObjectReference;



public class Message implements Serializable {


	private static final long serialVersionUID = -3134382594687183495L;
	
	RemoteObjectReference ref;
	msgType type;
	Object[] methodArgs;
	String methodName; 
	String reply;
	Object returnVal;
	String objectID;
	// sending reply message, string ans carry explanation for error or some warning
	public Message(String ans, msgType type) {
		reply= ans;
		this.type = type;
	}
	// sending invoke
	public Message(msgType type, Object[] args, String name, String objectID){
		this.methodArgs = args;
		this.type = type;
		this.methodName = name;
		this.objectID = objectID;
	}
	// sending the ror to client
	public Message(RemoteObjectReference ror, msgType passref) {
		type=passref;
		ref=ror;
		
	}
	// sending message carrying identifier
	public Message(msgType type, String objID){
		this.objectID = objID;
		this.type = type;
	}
	// sending invoke result object
	public Message(Object object, msgType val) {
		type=val;
		returnVal=object;
	}
	// sending rebind and bind message
	public Message(String serviceName2, Object ob, msgType rebind) {
	returnVal=ob;
	type=rebind;
	objectID=serviceName2;
	}

	public Message(msgType tp) {
		type=tp;
	}

	public msgType getResponType() {
	
		return this.type;
	}
	public String getMethodName() {
	
		return methodName;
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
	
	public RemoteObjectReference getROR(){
		return this.ref;
	}

	public String getMeg() {
		return reply;
	}
}
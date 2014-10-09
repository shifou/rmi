package data;
import java.io.Serializable;

import ror.RemoteObjectReference;



public class Message implements Serializable {


	private static final long serialVersionUID = -3134382594687183495L;
	
	RemoteObjectReference ref;
	String serviceName;
	msgType type;
	Object[] methodArgs;
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
	
	public Message(msgType type, String objID){
		this.objectID = objID;
		this.type = type;
	}

	public Message(Object object, msgType val) {
		type=val;
		returnVal=object;
	}

	public Message(String serviceName2, Object ob, msgType rebind) {
	returnVal=ob;
	type=rebind;
	serviceName=serviceName2;
	}

	public Message(msgType list) {
		type=list;
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
	
	public RemoteObjectReference getROR(){
		return this.ref;
	}

	public String getMeg() {
		return reply;
	}
}
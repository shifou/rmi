package data;
import java.io.Serializable;



public class Message implements Serializable {


	private static final long serialVersionUID = -3134382594687183495L;
	
	
	msgType type;
	Object[] methodArgs;
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
	
	public msgType getResponType() {
	
		return this.type;
	}
	public String getMethodName() {
	
		return methodName;
	}
	
}
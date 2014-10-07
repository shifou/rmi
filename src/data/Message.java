package data;
import java.io.Serializable;



public class Message implements Serializable {


	private static final long serialVersionUID = -3134382594687183495L;
	
	
	msgType tp;
	Object[] methodArgs;
	String methodName; 
	String reply;
	Object returnVal;
	public Message(String ans, msgType reply2) {
		reply= ans;
		tp=reply2;
	}
	public msgType getResponType() {
	
		return tp;
	}
	public String getMethodName() {
	
		return methodName;
	}
	
}
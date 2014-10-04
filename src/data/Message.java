package data;
import java.io.Serializable;
import java.net.InetAddress;
public class Message implements Serializable {

	msgType tp;
	String ip;
	String port;
	String classArgs;
	String serviceName;
	String reply;
	public Message(String ans, msgType reply2) {
		reply= ans;
		tp=reply2;
	}
	public msgType getResponType() {
		// TODO Auto-generated method stub
		return tp;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return serviceName;
	}
	
}
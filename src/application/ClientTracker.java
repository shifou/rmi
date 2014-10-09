package application;

import ror.Remote440;
import ror.Remote440Exception;

public interface ClientTracker extends Remote440 {

	public void login() throws Remote440Exception;
	public void logout() throws Remote440Exception;
	public int getTotalVisited() throws Remote440Exception;
	public void setVisitorName(String name) throws Remote440Exception;
	public String getLastVisitorName() throws Remote440Exception;
	public int getCurrentVisitors() throws Remote440Exception;
	
}

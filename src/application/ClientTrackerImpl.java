package application;

import java.io.Serializable;

import ror.Remote440Exception;

public class ClientTrackerImpl implements ClientTracker, Serializable {


	private static final long serialVersionUID = -9090809609648693263L;
	private int currentCount;
	private int totalCount;
	private String lastClientName;
	
	public ClientTrackerImpl(String name){
		this.currentCount = 0;
		this.lastClientName = name;
		this.totalCount = 0;
	}
	
	@Override
	public void login() throws Remote440Exception {
		this.currentCount += 1;
		this.totalCount += 1;

	}

	@Override
	public void logout() throws Remote440Exception {
		this.currentCount -= 1;

	}

	@Override
	public int getTotalVisited() throws Remote440Exception {
		return this.totalCount;
	}

	@Override
	public void setVisitorName(String name) throws Remote440Exception {
		this.lastClientName = name;
	}

	@Override
	public String getLastVisitorName() throws Remote440Exception {
		return this.lastClientName;
	}

	@Override
	public int getCurrentVisitors() throws Remote440Exception {
		return this.currentCount;
	}

}

package application;

import java.io.Serializable;

import ror.Remote440Exception;

public class TrackerImpl implements Tracker, Serializable {

	private static final long serialVersionUID = -9090809609648693263L;
	private int currentCount;
	private int totalCount;
	private String lastClientName;

	public TrackerImpl(String name) {
		this.currentCount = 0;
		this.lastClientName = name;
		this.totalCount = 0;
	}

	/**
	 * the login method updates states related to the number of clients that are
	 * currently logged in to the object, and the total number of clients that
	 * have logged in to the object
	 * */
	@Override
	public void login() throws Remote440Exception {
		this.currentCount += 1;
		this.totalCount += 1;

	}

	/**
	 * the logout method updates state related to the number of clients that are
	 * currently logged in to the object
	 * */
	@Override
	public void logout() throws Remote440Exception {
		this.currentCount -= 1;

	}

	/**
	 * the getTotalVisited method returns the total number of clients that have
	 * logged into the object
	 * */
	@Override
	public int getTotalVisited() throws Remote440Exception {
		return this.totalCount;
	}

	/**
	 * the setVisitorName(name) method sets the name of the last client visited
	 * to name in the object
	 * */
	@Override
	public void setVisitorName(String name) throws Remote440Exception {
		this.lastClientName = name;
	}

	/**
	 * the getLastVisitorName method returns the name of the client that last
	 * updated the visitor name
	 * */
	@Override
	public String getLastVisitorName() throws Remote440Exception {
		return this.lastClientName;
	}

	/**
	 * the getCurrentVisitors method returns the number of clients that are
	 * currently logged into the object
	 * */
	@Override
	public int getCurrentVisitors() throws Remote440Exception {
		return this.currentCount;
	}

}

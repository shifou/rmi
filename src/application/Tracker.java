package application;

import ror.Remote440;
import ror.Remote440Exception;

/*
 *  Tracker is the interface for an application of the
 *  framework. 
 **/
public interface Tracker extends Remote440 {

	/**
	 * the login method updates states related to the number of clients that are
	 * currently logged in to the object, and the total number of clients that
	 * have logged in to the object
	 * */
	public void login() throws Remote440Exception;

	/**
	 * the logout method updates state related to the number of clients that are
	 * currently logged in to the object
	 * */
	public void logout() throws Remote440Exception;

	/**
	 * the getTotalVisited method returns the total number of clients that have
	 * logged into the object
	 * */
	public int getTotalVisited() throws Remote440Exception;

	/**
	 * the setVisitorName(name) method sets the name of the last client visited
	 * to name in the object
	 * */
	public void setVisitorName(String name) throws Remote440Exception;

	/**
	 * the getLastVisitorName method returns the name of the client that last
	 * updated the visitor name
	 * */
	public String getLastVisitorName() throws Remote440Exception;

	/**
	 * the getCurrentVisitors method returns the number of clients that are
	 * currently logged into the object
	 * */
	public int getCurrentVisitors() throws Remote440Exception;

}

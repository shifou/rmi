package application;

import ror.Remote440;
import ror.Remote440Exception;

/*
 *  StringConcat is the interface for an application of the
 *  framework. 
 **/
public interface StringConcat extends Remote440 {

	/**
	 * concat(a, b) returns the concatenated String ab.
	 * */
	public String concat(String a, String b) throws Remote440Exception;

}

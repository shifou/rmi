package application;

import ror.Remote440;
import ror.Remote440Exception;
import ror.RemoteObjectReference;

/*
 *  Person is the interface for an application of the
 *  framework. 
 **/
public interface Person extends Remote440 {

	/**
	 * The first argument should be a FibonacciCalc type, while the second
	 * argument should be a StringConcat type, then returns the concatenated
	 * String, (first) (second) (nth Fibonacci number)
	 * */
	public String create(Remote440 a, Remote440 b, String first, String second,
			Integer n) throws Remote440Exception;

}

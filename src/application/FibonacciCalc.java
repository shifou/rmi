package application;

/*
 *  FibonacciCalc is the interface for an application of the
 *  framework. 
 **/
import ror.Remote440;
import ror.Remote440Exception;

public interface FibonacciCalc extends Remote440 {

	/**
	 * nthFibonacci(n) returns the nth Fibonacci number
	 * */
	public int nthFibonacci(Integer n) throws Remote440Exception;

}

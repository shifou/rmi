package application;

import ror.Remote440;
import ror.Remote440Exception;

public interface FibonacciCalc extends Remote440 {

	public int nthFibonacci(Integer n) throws Remote440Exception;
	
}

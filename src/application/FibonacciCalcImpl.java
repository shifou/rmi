package application;

import ror.Remote440Exception;

public class FibonacciCalcImpl implements FibonacciCalc {

	@Override
	public int nthFibonacci(Integer n) throws Remote440Exception {
		if (n == 0){
			return 0;
		}
		else if (n == 1){
			return 1;
		}
		else {
			return (nthFibonacci(n-1) + nthFibonacci(n - 2));
		}
	}

}

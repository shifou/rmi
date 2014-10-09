package application;


import java.io.Serializable;

import ror.Remote440Exception;

public class FibonacciCalcImpl implements FibonacciCalc, Serializable{


	private static final long serialVersionUID = 7772266491896322433L;

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

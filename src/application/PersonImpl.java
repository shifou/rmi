package application;

import java.io.Serializable;

import ror.Remote440;
import ror.Remote440Exception;

public class PersonImpl implements Person, Serializable {

	private static final long serialVersionUID = -5375907943175522127L;

	/**
	 * The first argument should be a FibonacciCalc type, while the second
	 * argument should be a StringConcat type, then returns the concatenated
	 * String, (first) (second) (nth Fibonacci number)
	 * */
	@Override
	public String create(Remote440 a, Remote440 b, String first, String second,
			Integer n) throws Remote440Exception {

		try {
			FibonacciCalc fib = (FibonacciCalc) a;
			StringConcat sc = (StringConcat) b;
			String name = sc.concat(first, second);
			int num = fib.nthFibonacci(n);
			return name + "\t" + num;

		} catch (Exception e) {

			throw new Remote440Exception();
		}

	}

}

package application;


import java.io.Serializable;

import ror.Remote440;
import ror.Remote440Exception;


public class PersonImpl implements Person, Serializable{

	private static final long serialVersionUID = -5375907943175522127L;

	

	@Override
	public String create(Remote440 a, Remote440 b) throws Remote440Exception {
		
		try {
			FibonacciCalc fib = (FibonacciCalc)a;
			StringConcat sc = (StringConcat)b;
			String name = sc.concat("Lixun", "Mao");
			int num = fib.nthFibonacci(10);
			return name+"\t"+num;
			
		} catch (Exception e){
			
			throw new Remote440Exception();
		}
		
	}

}

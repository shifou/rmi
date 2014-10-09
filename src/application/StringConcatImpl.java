package application;

import ror.Remote440Exception;

public class StringConcatImpl implements StringConcat {

	@Override
	public String concat(String a, String b) throws Remote440Exception {
		String ret = a + b;
		return ret;
	}

}

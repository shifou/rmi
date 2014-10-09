package application;

import java.io.Serializable;

import ror.Remote440Exception;

public class StringConcatImpl implements StringConcat, Serializable {

	private static final long serialVersionUID = -1795059548480184154L;

	/**
	 * concat(a, b) returns the concatenated String ab.
	 * */
	@Override
	public String concat(String a, String b) throws Remote440Exception {
		String ret = a + b;
		return ret;
	}

}

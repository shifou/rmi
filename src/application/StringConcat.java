package application;

import ror.Remote440;
import ror.Remote440Exception;

public interface StringConcat extends Remote440 {

	public String concat(String a, String b) throws Remote440Exception;
	
}

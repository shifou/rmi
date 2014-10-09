package application;

import ror.Remote440;
import ror.Remote440Exception;
import ror.RemoteObjectReference;

public interface Person extends Remote440 {

	public String create(Remote440 a, Remote440 b) throws Remote440Exception;
	
}

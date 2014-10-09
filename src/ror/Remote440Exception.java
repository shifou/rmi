package ror;

/*
 * Remote440Exception is the Exception class of the framework and this 
 * exception gets thrown whenever remote method invocations, stub creation,
 * etc. fail
 * */
public class Remote440Exception extends Exception {

	private static final long serialVersionUID = 5273352277223339104L;

	public Remote440Exception() {
		super();
	}

	public Remote440Exception(String message) {

		super(message);

	}

}

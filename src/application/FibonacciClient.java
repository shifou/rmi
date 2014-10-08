package application;

import java.io.IOException;
import java.net.UnknownHostException;

import main.LocalRegistry;
import ror.Remote440Exception;
import ror.RemoteObjectReference;


public class FibonacciClient {

	public static void main(String[] args){
		try {
			LocalRegistry reg = new LocalRegistry(args[0], Integer.parseInt(args[1]));
			RemoteObjectReference ror = reg.lookup("fibonacci1");
			FibonacciCalc fib = (FibonacciCalc)ror.localize();
			System.out.println(fib.nthFibonacci(5));
		}
		catch (UnknownHostException e){
			
		}
		catch (IOException e){
			
		}
		catch (ClassNotFoundException e){
			
		} catch (Remote440Exception e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

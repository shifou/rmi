package test;

import java.io.IOException;
import java.net.UnknownHostException;

import application.FibonacciCalc;
import main.LocalRegistry;
import ror.Remote440Exception;
import ror.RemoteObjectReference;


public class FibonacciClient {

	public static void main(String[] args){
		if(args.length!=2){
			System.out.format("Usage: ./server <registryIp> <registryPort>");
			return;
		}
		try {
			LocalRegistry reg = new LocalRegistry(args[0], Integer.parseInt(args[1]));
			RemoteObjectReference ror = reg.lookup("fibonacci1");
			FibonacciCalc fib = (FibonacciCalc)ror.localize();
			System.out.println(fib.nthFibonacci(6));
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

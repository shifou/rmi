package test;

import java.io.IOException;
import java.net.UnknownHostException;

import main.LocalRegistry;
import ror.Remote440Exception;
import ror.RemoteObjectReference;
import application.Person;
import application.StringConcat;

public class PersonClient {

	public static void main(String[] args){
		if(args.length!=2){
			System.out.format("Arguments should be of the following form: <registryIp> <registryPort>");
			return;
		}
		try {
			LocalRegistry reg = new LocalRegistry(args[0], Integer.parseInt(args[1]));
			RemoteObjectReference sc = reg.lookup("stringconcat1");
			RemoteObjectReference fib = reg.lookup("fibonacci1");
			RemoteObjectReference person = reg.lookup("person1");
			Person p = (Person)person.localize();
			System.out.println(p.create(fib, sc, "Lixun", " Mao", 10));
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

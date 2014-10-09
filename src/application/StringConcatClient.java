package application;

import java.io.IOException;
import java.net.UnknownHostException;

import main.LocalRegistry;
import ror.Remote440Exception;
import ror.RemoteObjectReference;

public class StringConcatClient {

	public static void main(String[] args){
		if(args.length!=2){
			System.out.format("Usage: ./server <registryIp> <registryPort>");
			return;
		}
		try {
			LocalRegistry reg = new LocalRegistry(args[0], Integer.parseInt(args[1]));
			RemoteObjectReference ror = reg.lookup("stringconcat1");
			StringConcat scon = (StringConcat)ror.localize();
			System.out.println(scon.concat("Druhin ", "Sagar Goel"));
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

package test;

import java.io.IOException;
import java.net.UnknownHostException;

import application.Tracker;
import main.LocalRegistry;
import ror.Remote440Exception;
import ror.RemoteObjectReference;


public class TrackerClient {

	public static void main(String[] args){
		if(args.length!=2){
			System.out.format("Arguments should be of the following form: <registryIp> <registryPort>");
			return;
		}
		try {
			LocalRegistry reg = new LocalRegistry(args[0], Integer.parseInt(args[1]));
			RemoteObjectReference ror = reg.lookup("tracker1");
			// creating 3 clients
			Tracker tc1 = (Tracker)ror.localize();
			Tracker tc2 = (Tracker)ror.localize();
			Tracker tc3 = (Tracker)ror.localize();
			// initial variable should be server
			System.out.println("Initial name: "+tc1.getLastVisitorName());
			System.out.println("Intital current visitors: " + tc1.getCurrentVisitors());
			System.out.println("Intital total visitors: " + tc1.getTotalVisited());
			// login will increase the visitor count
			tc1.login();
			tc2.login();
			System.out.println("Current visitors: " + tc1.getCurrentVisitors());
			System.out.println("Total visitors: " + tc2.getTotalVisited());
			tc3.login();
			System.out.println("Current visitors: " + tc1.getCurrentVisitors());
			System.out.println("Total visitors: " + tc3.getTotalVisited());
			//login will decrease the current visitor count
			tc2.logout();
			// update the visitor name
			tc1.setVisitorName(new String("TC 1"));
			System.out.println("Current visitors: " + tc1.getCurrentVisitors());
			System.out.println("Total visitors: " + tc3.getTotalVisited());
			System.out.println("Current name: " + tc3.getLastVisitorName());
			tc1.logout();
			tc3.logout();
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

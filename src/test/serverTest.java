package test;

import application.*;
import main.LocalRegistry;

public class serverTest{
	public static void main(String args[]) throws Exception {
		if(args.length!=2){
			System.out.format("Usage: ./serverTest <registryIp> <registryPort>");
			return;
		}
		// when server start
		// already has application.FibonacciCalcImpl fibonacci1
		//			   application.StringConcatImpl stringconcat1
		//			   application.PersonImpl person1
		LocalRegistry reg = new LocalRegistry(args[0], Integer.parseInt(args[1]));
		FibonacciCalcImpl fib= new FibonacciCalcImpl();
		TrackerImpl t = new TrackerImpl("Server");
		//bind tracker1
		reg.bind("tracker1", t);
		System.out.println(reg.list());
		System.out.println("----");
		// bind testname
		System.out.println(reg.bind("testname",fib));
		System.out.println("----");
		System.out.println(reg.list());
		System.out.println(reg.unbind("testname"));
		System.out.println("----");
		System.out.println(reg.list());
		System.out.println("----");
		// rebind twice
		System.out.println(reg.rebind("testname", (Object)fib));
		System.out.println(reg.rebind("testname", (Object)fib));
		System.out.println(reg.list());
		System.out.println("----");
		// unbind test which does not exist in the server
		System.out.println(reg.unbind("test"));
		System.out.println(reg.unbind("testname"));
		System.out.println(reg.list());
	}
}
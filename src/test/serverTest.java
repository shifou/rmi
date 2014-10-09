package test;

import application.*;
import main.LocalRegistry;

public class serverTest{
	public static void main(String args[]) throws Exception {
		if(args.length!=2){
			System.out.format("Usage: ./serverTest <registryIp> <registryPort>");
			return;
		}
		LocalRegistry reg = new LocalRegistry(args[0], Integer.parseInt(args[1]));
		FibonacciCalcImpl fib= new FibonacciCalcImpl();
		TrackerImpl t = new TrackerImpl("Server");
		reg.bind("tracker1", (Object)t);
		System.out.println(reg.list());
		System.out.println("----");
		System.out.println(reg.bind("testname", (Object)fib));
		System.out.println("----");
		System.out.println(reg.list());
		System.out.println(reg.unbind("testname"));
		System.out.println("----");
		System.out.println(reg.list());
		System.out.println("----");
		System.out.println(reg.rebind("testname", (Object)fib));
		System.out.println(reg.rebind("testname", (Object)fib));
		System.out.println(reg.list());
		System.out.println("----");
		System.out.println(reg.unbind("test"));
		System.out.println(reg.unbind("testname"));
		System.out.println(reg.list());
	}
}
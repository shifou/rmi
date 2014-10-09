/*
 * Server class used for listening from client request 
 * 
 */
package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Server {
	
	// global registry
	public static Registry reg;

	public static void main(String args[]) throws Exception {
		int registryPort = 0;
		if (args.length > 2) {
			System.out
					.format("Usage1: ./Server <registryPort> <service_list>\nUsage2: ./Server <registryPort> <service_list>");
			return;
		}
		try {
			registryPort = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("please input Integer as the port");
			return;
		}
		// read class conf from file if available
		// one line one class conf
		ArrayList<String> services = new ArrayList<String>();
		if (args.length == 2) {
			String serviceList = args[1];

			try {
				File file = new File(serviceList);
				BufferedInputStream fis = new BufferedInputStream(
						new FileInputStream(file));
				BufferedReader b = new BufferedReader(new InputStreamReader(
						fis, "utf-8"));
				String line;
				while ((line = b.readLine()) != null) {
					services.add(line);
				}
				b.close();
			} catch (Exception e) {
				System.out.println("file list not found");
				return;
			}
		}
		reg = new Registry(registryPort);
		if (reg == null) {
			System.out.println("bind registry port error");
			System.exit(-1);
		}
		// if conf file exist we bind those service in advance
		if (args.length == 2)
			reg.addServices(services);
		// begin listen
		reg.listen();
		System.out.println("Server close");
	}
}
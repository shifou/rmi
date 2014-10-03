/*
 * Server class used for listening from slaves join information 
 * and create the connection thread
 */
package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import data.msgType;

public class Server {
	public static void main(String args[]) throws Exception {
		int registryPort=0;
		if (args.length != 2) {
			System.out.format("Usage: ./Server <registryPort> <service_list>");
		}
		try {
			registryPort = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("please input Integer as the port");
			return;
		}
		String serviceList = args[1];
		ArrayList<String> serviceNames=new ArrayList<String>(); 
		try {
			File file = new File(serviceList);
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file));
			BufferedReader b = new BufferedReader(new InputStreamReader(fis,
					"utf-8"));
			String line;
			while ((line = b.readLine()) != null) {
				serviceNames.add(line);
			}

		} catch (Exception e) {
			System.out.println("file list not found");
			return;
		}
		Registry a;
			a = new Registry(registryPort);
		if(a==null){
			System.out.println("bind registry port error");
			System.exit(-1);
		}
		a.addService(serviceNames);
		a.listen();
		System.out.println("Server close");
	}
}
package com.luan.obterMac;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

public class MacPublic {

	public static String obterEnderecoMAC() throws SocketException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while(interfaces.hasMoreElements()) {
			NetworkInterface iface = interfaces.nextElement();
			byte[] mac = iface.getHardwareAddress();
			if(mac != null) {
				StringBuilder sb = new StringBuilder();
				for(byte b : mac) {
					sb.append(String.format("%02X: ", b));
				}
				if(sb.length() > 0) {
					sb.deleteCharAt(sb.length() - 1);
				}
				return sb.toString();
			}
		}
		return null;
	}
	
	public static String obterEnderecoIPPub() throws Exception {
		URL url = new URL("http://httpbin.org/ip");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuilder content = new StringBuilder();
		while((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		
		return content.toString();
	}
	
	public static void main(String[] args) {
		try {
			String mac = obterEnderecoMAC();
			String ipPublico = obterEnderecoIPPub();
			
			System.out.println("Endereço MAC: " + mac);
			System.out.println("Endereço IP Público: " + ipPublico);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

package com.tiim.transaction.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;

import com.tiim.fileutil.AppendToFile;
import com.tiim.util.Cryptography;

public class ValidateCredential extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)  
			throws ServletException,IOException  
	{  
			res.setContentType("text/html");//setting the content type  
			String folderName = req.getSession().getServletContext().getRealPath("uploaddocument");
			folderName = folderName +"/config/";
			System.out.println("folderName: "+folderName);
			List<String> content  = AppendToFile.readFile(folderName);
			InetAddress ip;
			StringBuilder sb = new StringBuilder();
			Cryptography crypt = new Cryptography();
			try {

				ip = InetAddress.getLocalHost();
				NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				byte[] mac = network.getHardwareAddress();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}
				String fileContent = new String();
				String ipAddress = new String();
				String macAddress = new String();
				String systemIP = ip.getHostAddress();
				String systemMAC = sb.toString();
				Iterator<String> itr = content.iterator();
				while(itr.hasNext())
				{
					fileContent = itr.next();
					fileContent = crypt.decrypt(fileContent);
					if(fileContent.contains("ipAddress"))
					{
						ipAddress = fileContent;
					}
					if(fileContent.contains("macAddress"))
					{
						macAddress = fileContent;
					}
				}
				if(ipAddress.equalsIgnoreCase("ipAddress:"+systemIP))
				{
					System.out.println("ipaddress::::::::::::::::: "+ipAddress);
				}else
				{
					AppendToFile.appendFile(folderName, "ipAddress:"+systemIP);
					AppendToFile.appendFile(folderName, "macAddress:"+systemMAC);
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e){
				e.printStackTrace();
			}catch (Exception e) {
				System.out.println("Exception when check for validate credential: "+e.getMessage());
			}
			
	}
}

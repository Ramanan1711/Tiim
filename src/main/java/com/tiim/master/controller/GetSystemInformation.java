package com.tiim.master.controller;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GetSystemInformation {

	@RequestMapping(value = "/getSystemInfomration", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getSystemInfomration(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("systemInformation");
		
		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
			modelView.addObject("ipAddress", ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			System.out.println(sb.toString());
			modelView.addObject("macAddress", sb.toString());
		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e){

			e.printStackTrace();

		}
		
		return modelView;
	}
}

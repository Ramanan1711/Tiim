package com.tiim.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SendMail {
	
	
	@Autowired
	DataSource datasource;
	
	
		
	public String sendIssueApproved(String toContactEmail, String mailMessage, String subject)
	{
		String messageStatus = "Mail Sent";
		String mailbody = "";
		//String subject = "Receipt screen has been approved";
		String fromMail = "patinspectiontool@gmail.com";
			try
			{				
				System.out.println("send mail");
				mailbody = "<table width='550px' cellspacing='3' cellpadding='5' align='center' border='0' style='font-size:12px;font-family:Tahoma;color:#333333'>";
				mailbody = mailbody + "<tbody><tr><td style='height:10px;width:24%;'></td><td style='height:10px;width:1%;'></td><td style='height:10px;width:75%;'></td></tr>";
				mailbody = mailbody + "<tr><td colspan='3' style='height:10px;'></td></tr>";			 						
				mailbody = mailbody + "<tr style='valign:middle;'><td colspan='3'>The receipt transaction is: <b>"+mailMessage+"</b></td></tr>";
				mailbody = mailbody + "</tbody></table>";
				
		
				String host = "127.0.0.1";
				//String host = "smtp.epaexports.com";
	//    		Properties props = new Properties();
	//    		props.put("mail.host",host);
	//    		//props.put("mail.smtp.host",host);
	//    		props.put("mail.transport.protocol","smtp");
	//    		Session session = Session.getInstance(props,null);
				
				final String username = "patinspectiontool@gmail.com";
				final String password = "pattool@123";
	
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true"); 
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
	
				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
	    		
	    	
				MimeMessage message = new MimeMessage(session);
	
				InternetAddress from = new InternetAddress(fromMail.trim());
				message.setFrom(from);				
	
				String[] tomail = toContactEmail.split(";"); 
		        InternetAddress[] toAddress = new InternetAddress[tomail.length];
		        for(int i =0; i < tomail.length ; i++)
				{  
		         		toAddress[i] = new InternetAddress(tomail[i]);
				}	         
				 
				    for( int i=0; i < toAddress.length; i++) { 
				        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				    }	
				/*
				InternetAddress to = new InternetAddress(toMail.trim());
				message.addRecipient(Message.RecipientType.TO, to);	*/
				//InternetAddress cc1 = new InternetAddress(cc);
				//message.addRecipient(Message.RecipientType.CC, cc1);
				//message.addRecipient(Message.RecipientType.BCC, new InternetAddress("ranikkarasu@gmail.com"));
				message.setSubject(subject);			
				message.setContent(mailMessage, "text/html");
				Transport.send(message);
			}
			catch(Exception e)
			{
				System.out.println("Exception while sending the Support mail : "+e.getMessage());
				messageStatus = "Error ! Please try after.";
				e.printStackTrace();
			}

		return messageStatus;
	}
	
	public String sendActivatedMail(String toMail, String mailMessage)
	{
		String messageStatus = "Mail Sent";
		String mailbody = "";
		String subject = "Your offerbrite account got activated";
		String fromMail = "offerbrite@gmail.com";
			try
			{				
				mailbody = "<table width='550px' cellspacing='3' cellpadding='5' align='center' border='0' style='font-size:12px;font-family:Tahoma;color:#333333'>";
				mailbody = mailbody + "<tbody><tr><td style='height:10px;width:24%;'></td><td style='height:10px;width:1%;'></td><td style='height:10px;width:75%;'></td></tr>";
				mailbody = mailbody + "<tr><td colspan='3' style='height:10px;'></td></tr>";			 						
				mailbody = mailbody + "<tr style='valign:middle;'><td colspan='3'>Your password is: <b>"+mailMessage+"</b></td></tr>";
				mailbody = mailbody + "</tbody></table>";
				
		
				String host = "127.0.0.1";
				//String host = "smtp.epaexports.com";
	//    		Properties props = new Properties();
	//    		props.put("mail.host",host);
	//    		//props.put("mail.smtp.host",host);
	//    		props.put("mail.transport.protocol","smtp");
	//    		Session session = Session.getInstance(props,null);
				
				final String username = "offerbriteportal@gmail.com";
				final String password = "Offerbrite@123";
	
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true"); 
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
	
				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
	    		
	    	
				MimeMessage message = new MimeMessage(session);
	
				InternetAddress from = new InternetAddress(fromMail.trim());
				message.setFrom(from);				
	
				InternetAddress to = new InternetAddress(toMail.trim());
				message.addRecipient(Message.RecipientType.TO, to);	
				//InternetAddress cc1 = new InternetAddress(cc);
				//message.addRecipient(Message.RecipientType.CC, cc1);
				//message.addRecipient(Message.RecipientType.BCC, new InternetAddress("ranikkarasu@gmail.com"));
				message.setSubject(subject);			
				message.setContent(mailMessage, "text/html");
				Transport.send(message);
			}
			catch(Exception e)
			{
				System.out.println("Exception while sending the Support mail : "+e.getMessage());
				messageStatus = "Error ! Please try after.";
				e.printStackTrace();
			}

		return messageStatus;
	}
	
	
	
	
	/*public static void main(String arg[])
	{
		SendMail mail = new SendMail();
		mail.sendEPASupportMail("ranikkarasu@yahoo.co.in", "ranikkarasu@gmail.com", "mail message");
	}*/
}

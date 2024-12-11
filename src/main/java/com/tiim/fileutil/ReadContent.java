package com.tiim.fileutil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ReadContent {

	
	public String getEmailContent(String filePath, Map<String, String> input)
	{
		String content = readContentFromFile(filePath);
		try{
			Set<Entry<String, String>> setEntry = input.entrySet();
			for(Map.Entry<String, String> mapEntry: setEntry)
			{
				String key = mapEntry.getKey();
				String value = mapEntry.getValue();
				content = content.replace(key, value);
			}
		}catch(Exception ex)
		{
			System.out.println("Error when read the email template: "+ex.getMessage());
			ex.printStackTrace();
		}
		
		return content;
	}
	
	//Method to read HTML file as a String 
		
		private String readContentFromFile(String fileName)
		{
			StringBuffer content = new StringBuffer();
			try
			{
				BufferedReader bfReader = new BufferedReader(new FileReader(fileName));
				try
				{
					String line = null;
					while((line = bfReader.readLine()) != null)
					{
						content.append(line);
						content.append(System.getProperty("line.separator"));
					}
				}finally
				{
					bfReader.close();
				}
				
			}catch(Exception ex)
			{
				System.out.println("Error when read the content from html: "+ex.getMessage());
				ex.printStackTrace();
			}
			return content.toString();
		}
}

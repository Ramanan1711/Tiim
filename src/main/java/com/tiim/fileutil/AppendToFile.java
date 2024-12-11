package com.tiim.fileutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tiim.util.Cryptography;

public class AppendToFile {
	
	private static final String FILENAME = "licence.cfg"; 
	private static Cryptography cryptography = new Cryptography();
	
	public static void appendFile(String dir, String content)
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {

			content = cryptography.encrypt(content);
			File file = new File(dir + FILENAME);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			
			bw.write(content);
			bw.write("\n");

			System.out.println("Done: "+file.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception ex){
			System.out.println("Exception in append a file: "+ex.getMessage());
		}
		finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

	}
	
	public static List<String> readFile(String dir)
	{
		String sCurrentLine;
		List<String> lstContent = new ArrayList<>();
		//String fileContent = "";
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
				while ((sCurrentLine = br.readLine()) != null) {
					//fileContent = fileContent + sCurrentLine+"\n";
					lstContent.add(sCurrentLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		return lstContent;
	}

	/*public static void main(String[] args) {
		String content = readFile();
		System.out.println("content: "+content);
	}*/
	
	public static void checkFileCotent()
	{
		
	}


}

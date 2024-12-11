package com.tiim.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tiim.model.ReportFiles;

@Service
public class GetReportBasedOnProductDetails {

	public List<ReportFiles> getProductFiles(String rootFolder, String destFolder, String productName, String toolingLotNumber, String requestId)
	{
		ReportFiles reportFiles = null;
		List<ReportFiles> fileList = new ArrayList<ReportFiles>();
		File directory = null;
		File desDirectory = null;
		if(requestId !=null && requestId.trim().length() > 0)
		{
			directory = new File(rootFolder+"/"+toolingLotNumber+"/"+requestId);
			desDirectory = new File(destFolder+"/"+productName);
			System.out.println("desDirectory: "+desDirectory.exists());
			if(!desDirectory.exists())
			{
				desDirectory.mkdir();
				
			}
			desDirectory = new File(destFolder+"/"+productName+"/"+toolingLotNumber);
			if(!desDirectory.exists())
			{
				desDirectory.mkdir();
				
			}
			desDirectory = new File(destFolder+"/"+productName+"/"+toolingLotNumber+"/"+requestId);
			System.out.println("desDirectory: "+desDirectory.exists());
			if(!desDirectory.exists())
			{
				desDirectory.mkdir();
			}
		}else
		{
			directory = new File(rootFolder+"/"+toolingLotNumber);
			desDirectory = new File(destFolder+"/"+productName);
			System.out.println("desDirectory: "+desDirectory.exists());
			if(!desDirectory.exists())
			{
				desDirectory.mkdir();
				
			}
			desDirectory = new File(destFolder+"/"+productName+"/"+toolingLotNumber);
			if(!desDirectory.exists())
			{
				desDirectory.mkdir();
				
			}
		}
        //get all the files from a directory
        File[] fList = directory.listFiles();
        if(fList != null)
        {
	        for (File file : fList){
	        	System.out.println("flist: "+file);
	           
	            if(file.isDirectory())
	            {
	            	File subDirectory = new File(rootFolder+"/"+toolingLotNumber+"/"+file.getName());
	            	File desSubDirectory = new File(destFolder+"/"+productName+"/"+toolingLotNumber+"/"+file.getName());
	            	//File desSubDirectory = new File(destFolder+"/"+productName+"/"+toolingLotNumber);
	            	if(!desSubDirectory.exists())
	            	{
	            		desSubDirectory.mkdir();
	            	}
	            	File[] subFileList = subDirectory.listFiles();
	            	for(File subFile : subFileList)
	            	{ 
	            		File toCopypath = new File(rootFolder+"/"+toolingLotNumber+"/"+file.getName()+"/"+subFile.getName());
	            		File copyFilePath = new File(destFolder+"/"+productName+"/"+toolingLotNumber+"/"+file.getName()+"/"+subFile.getName());
	            		//File copyFilePath = new File(destFolder+"/"+productName+"/"+toolingLotNumber+"/"+file.getName());
	            		try {
							Files.copy(toCopypath.toPath(), copyFilePath.toPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("can not copy::: "+e.getMessage());
							e.printStackTrace();
						}
	            		reportFiles = new ReportFiles();
	            		reportFiles.setFileNamePath("./ReportFiles/"+productName+"/"+toolingLotNumber+"/"+file.getName()+"/"+subFile.getName());
	            		reportFiles.setFileName(file.getName()+"/"+subFile.getName());
	            		fileList.add(reportFiles);
	            	}
	            }else
	            {
	            	File toCopypath = new File(rootFolder+"/"+toolingLotNumber+"/"+requestId+"/"+file.getName());
            		File copyFilePath = new File(destFolder+"/"+productName+"/"+toolingLotNumber+"/"+requestId+"/"+file.getName());
	            	//File copyFilePath = new File(destFolder+"/"+productName+"/"+toolingLotNumber+"/"+requestId);
	            	File desSubDirectory = new File(destFolder+"/"+productName+"/"+toolingLotNumber+"/"+requestId);
	            	if(!desSubDirectory.exists())
	            	{
	            		desSubDirectory.mkdir();
	            	}
	            	
	            	try {
	            		/*FileReader fr= null;
		            	FileWriter fw = null;
		            	fr=new FileReader(rootFolder+"/"+productName+"/"+toolingLotNumber+"/"+requestId+"/"+file.getName());
		                 fw=new FileWriter(destFolder+"/"+productName+"/"+toolingLotNumber+"/"+requestId+"/"+file.getName());
		                int c=fr.read();
		                while(c!=-1) {
		                  fw.write(c);
		                }
		                */
            		
						Files.copy(toCopypath.toPath(), copyFilePath.toPath());
					} catch (IOException e) {
						System.out.println("Exception: "+e.getMessage());
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}finally
					{
					/*	try{
						if(fr != null)
						{
							fr.close();
						}
						if(fw != null)
						{
							fw.close();
						}
						}catch(Exception exp)
						{
							System.out.println("error when close the file: "+exp.getMessage());
						}*/
					}
	            	reportFiles = new ReportFiles();
            		reportFiles.setFileNamePath("./ReportFiles/"+productName+"/"+toolingLotNumber+"/"+requestId+"/"+file.getName());
            		//System.out.println("file name: "+file.getName());
            		reportFiles.setFileName(file.getName());
	            	 fileList.add(reportFiles);
	            }
	
	        }
        }
        return fileList;
	}
}

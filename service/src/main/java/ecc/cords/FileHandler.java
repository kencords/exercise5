package ecc.cords;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FileHandler {
	
	private static String fileName;
	private static File file;
	
	public static String readFile(){
    	int retries = (fileName.equals("Default.txt")? 1 : 0);
    	String data = "";
    	while(retries <= 1){
	    	try{
	    		File file = new File(fileName);
				if(fileName.equals("Default.txt")){
					InputStream in = FileHandler.class.getClassLoader().getResourceAsStream("files/Default.txt");
					FileUtils.copyInputStreamToFile(in, file);
				}
				data = FileUtils.readFileToString(file,"UTF-8");
	    	}catch(IOException exception){
	    		if(retries == 1 && data.equals("")){
	        		createFile();
	        		continue;
	        	}
	        	System.out.println(exception.getMessage() + "\nSwitching to Default.txt...");
	        	setFileName("Default.txt");
	        	retries++;
	        	continue;
	    	}
	    	break;
	    }
		return data;
	}
	
	public static void writeToFile(String data){
		try{
			file = new File(fileName);
			FileUtils.writeStringToFile(file,data,"UTF-8");
		}catch(IOException exception){
			System.out.println(exception.getMessage() + "\nExiting Program...");
            System.exit(0);
		}	
	}
	
	public static void setFileName(String name){
		fileName = name;
		System.out.println(fileName);
	}

	private static void createFile(){
		file = new File(fileName);
		try{
			file.createNewFile();
		}catch(IOException e){
			System.out.println(e.getMessage() + "\n Can't create Default.txt file. Exiting Program");
			System.exit(0);
		}
	}
}

package ecc.cords;

import java.util.List;

public class ServiceUtils{

	public static int countPatternOccurence(String word, String pattern){
		if(pattern.length() > word.length())
			return 0;
		int count = 0;
		for(int i = 0; i <= word.length()-pattern.length(); i++){
			if(word.substring(i,i+pattern.length()).equals(pattern))
				count++;
		}
		return count;	
	}
	
	public static boolean isValidIndex(int val, int max, String varName) throws IndexOutOfBoundsException{
		if(val < 0 || val >= max){
			throw new IndexOutOfBoundsException("Invalid " + varName + " index! " + varName
					+ " must be greater than 0 and less than " + max);
		}
		return true;
	}
	
	public static String generateRandomString(int length){
		String randomString = "";
		
		for(int i=0; i<length; i++){
			randomString +=  (char)((Math.random() * 81) + 174);
		}
		return randomString;
	}

	public static void saveToFile(Table table){
		StringBuilder data = new StringBuilder();
		String delimiter = generateRandomString(5).trim();
		
		data.append(delimiter + System.lineSeparator());
		
		for(int y = 0; y < table.getSize(); y++){
			Row tableRow = table.getRowList().get(y);
			for(int x = 0; x < tableRow.getSize(); x++){
				data.append((x!=0? delimiter : "") + tableRow.getCells().get(x).getKey() + delimiter + 
					tableRow.getCells().get(x).getValue());
			}
			data.append(System.lineSeparator());
		}
		FileHandler.writeToFile(data.toString());
	}
}
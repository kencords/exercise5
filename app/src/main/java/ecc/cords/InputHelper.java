package ecc.cords;

import java.util.Scanner;

public class InputHelper{

	private static Scanner input = new Scanner(System.in);

	public static String askString(String msg){
		System.out.print(msg);
		return input.nextLine();
	}

	public static int askNumericInput(String varName) throws Exception{
		System.out.print("Enter numeric value for " + varName + ": ");
		String tmp = input.nextLine();
		if(!isValidNumericInput(tmp)){
			throw  new Exception("Invalid " + varName + " input! " + varName + " must be a number.");
		}

		return Integer.parseInt(tmp);		
	}
	
	public static boolean isValidNumericInput(String var){
		boolean isValid = true;
		for(int index = 0; index < var.length(); index++){
			if(var.length() > 1 && index == 0 && (var.charAt(index) == '-' || var.charAt(index) == '+'))
				continue;
			if(!Character.isDigit(var.charAt(index))){
				isValid = false;
				break;
			}
		}
		return isValid;
	}	
}
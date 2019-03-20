/**
* This program will parse a text file, tokenize the contents, and count the number of occurences of each token. 
* The content will be stored into 2 parallel arrays: one array with the token, and one array with the token count.
* User is able to choose a second input file of words to be ignored. 
* User can then select a location/file name for analysis output to be stored.
* The analysis will include a sorted list of tokens, along with the occurence counts of each lower-case token, separated by commas.
*
*
* @author Xinmei Guo
* @since 2/1/2019
*
*
*/

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Prog01_Tokenization {

	/**
	 * IsStringNull
	 * Checks if a string is not null. This helps determine if a string is empty (null).
	 * @param str: string value to be evaluated
	 * @return false if the string has a word in it
	 * @return true if the string is null
	 */
	
	public static boolean IsStringNull(String str) {
		if (str != null) {
		    return false;
		}
		else {
		    return true;
		}
	}
	
	
	/**
	 * FormatArray
	 * Formats words so that they are all lower case, removes invalid characters at the beginning and end of word
	 * @param arr: array
	 * @param count: size of array
	 * @return arr: formatted array
	 */
	public static String[] FormatArray(String[] arr, int count) {
		for (int i=0; i<count; i++) {
			arr[i] = arr[i].toLowerCase();
		}
		char c1, c3;
		
		//Looping through arr array
		for (int i=0; i<count; i++) {
			c1 = arr[i].charAt(0);	//1st char
			boolean validchar;
			int end;
			
			 //Checks if first character has punctuation
			//Dollar signs don't count
			 if (!Character.isDigit(c1) && !Character.isLetter(c1) && c1!='$') {
				 arr[i] = arr[i].substring(1, arr[i].length());
			 }
			 
			//Checks if last part of word has punctuation
			//While loop repeats until the last character is valid
			validchar = false;
			 
			while (!validchar) {	
				end = arr[i].length()-1;
				c3 = arr[i].charAt(end);
				if (!Character.isDigit(c3) && !Character.isLetter(c3)){
					arr[i] = arr[i].substring(0, arr[i].length()-1);
					end--;
				}
				else {
					validchar = true;
				}
			}
		}
		return arr;
	}

	
	/**
	 * CountNonNullArray
	 * Counts non-null elements in an array.
	 * In this program, the null elements are duplicates, because they are not copied over from the original array
	 * @param arr: array
	 * @param count: size of array
	 * @return uniqueCount: number of unique tokens
	 */
	
	public static int CountNonNullArray(String[] arr) {
		
		//Count unique non-null tokens
		int uniqueCount=0;
		for (int i=0; i<arr.length; i++) {
			if (IsStringNull(arr[i])==false){
				uniqueCount++;
			}
		}
		return uniqueCount;
	}
	
	/**
	 * CountNonNullArray
	 * Checks if menu choice is valid and prompts user to keep re-entering if they enter an invalid choice
	 * User can enter y or n in this program
	 * @param in: Scanner
	 * @param m: array of acceptable choices
	 * @param mName: name of the type of choice
	 */
	public static String isMenuValid(Scanner in, String[] m, String mName) {
		while(true) {
			String d = in.next();	
			for (int i = 0; i < m.length; i++) {
				if (d.equals(m[i])) 
					return d;
			}
			System.out.printf("Invalid %s (%s) entered.\n", mName, d); 
			System.out.printf("Enter %s: ", mName);
		}//while
	}// end isMenuValid	
	
	
	public static void main(String[] args)  throws FileNotFoundException{
		
		//Defining variables
		int tokenCount = 0, c=0;	//number of tokens, counter for final array
		int uniqueTokenCount=0, ignoreCount=0;
		String [] ignore = new String[1]; //words to be ignored
		String menu; //menu for ignoring words
		String[] yesNo = {"y", "n"}; 

		//prompt user for input
		System.out.println("TOKENIZATION");
		System.out.println("Select a text file to be analyzed.");
		
		//Scanning input text file into array
		String [] tokens = Tokenization.ScanIntoArray();
		
		//Size of array
		tokenCount = CountNonNullArray(tokens);
		
		//Format array to remove invalid characters and lower case everything
		tokens = FormatArray(tokens, tokens.length);
		
		//Sort all tokens, including duplicates
		Arrays.sort(tokens);

		//Copy over unique tokens; duplicates are not copied (null)
		//Count tokens
		int [] count = new int[tokenCount];
		String [] tokensUnique = new String[tokenCount];
		
		//Checking for unique tokens
		for (int i=0; i<tokensUnique.length; i++) {
			if (i==0) { //1st element always unique
				tokensUnique[0] = tokens[0];
			}
			//Checking to see if current element is equal to previous element
			//If not equal, copy the value over to new array
			if (i>0) {
				if (!tokens[i-1].equals(tokens[i])) {
					tokensUnique[i]=tokens[i];
				}
			}//if
			
			//Count tokens
			//If there is a match, increase counter
			for (int j=0; j<tokensUnique.length; j++) {
				if (tokens[i].equals(tokens[j])) {
					count[i]++;
				}
			}//for

		}//for
	
		//Count non-null elements in array
		uniqueTokenCount = CountNonNullArray(tokensUnique);

		//OPTION TO IGNORE WORDS
		System.out.println("Do you want to select a file of words to be ignored? (y/n)");

		//Checks that user input is valid
		Scanner inYN = new Scanner(System.in);
		menu = isMenuValid(inYN, yesNo, "y or n");
		
		//Stores words to be ignored into array
		if (menu.equals("y")) {
			ignore = Tokenization.ScanIntoArray();
			ignoreCount = CountNonNullArray(ignore);
			ignore = FormatArray(ignore, ignoreCount);
			ignore= Arrays.copyOf(ignore, ignoreCount);
		}
		
		//Make parallel arrays
		uniqueTokenCount -= ignoreCount;	//unique tokens = tokens minus words to be ignored
		int [] countFinal = new int[uniqueTokenCount];
		String [] tokensFinal = new String[uniqueTokenCount];
		
		//Copy over unique values only
		//c is a counter for the new array being filled
		while (c<uniqueTokenCount) {	//unique tokens
			for (int i=0; i<tokenCount; i++) {	//total tokens
				//if value is not null, copy it over
				check:
				if (IsStringNull(tokensUnique[i])==false){
					
					if (ignoreCount > 0) {
					for (int w=0; w<ignoreCount; w++) {
						if (tokensUnique[i].equals(ignore[w])){
							break check;
							}
						}
					}
					tokensFinal[c]=tokensUnique[i];
					countFinal[c]=count[i];
					c++;
				}//if	
			}//for
		}//while
		
		//Output into text file
		System.out.println("Choose a location to save the text file results.");
		Tokenization.OutputTokens(tokensFinal, countFinal);
	}

}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;

public class Tokenization {

	/**
	 * ScanIntoArray
	 * Scans user selected input text file into an array
	 * @param array: array with all tokens
	 */
	
	public static String[] ScanIntoArray() throws FileNotFoundException{

		JFileChooser chooser = new JFileChooser();
		
		Scanner in = null;
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			in = new Scanner(selectedFile);
		}
		
		String [] array= ArrayListToArray(in);
		return array;
	}
	
	/**
	 * ArrayListToArray
	 * Takes in input from Scanner into an array list
	 * Transfers content from array list to array
	 * @param in: Scanner
	 * @param array: array with tokens from input text file
	 */
	
	
	public static String[] ArrayListToArray(Scanner in){
		
		 ArrayList<String> tokenlist = new ArrayList<String>();
		 while (in.hasNext()) {
		    tokenlist.add(in.next());
		 }
		 String [] array = new String[tokenlist.size()];
		 for (int i=0; i<tokenlist.size(); i++) {
			 array[i] = tokenlist.get(i);
		 }
		 return array;
	}
	
	
	/**
	 * OutputTokens
	 * Outputs results into a text file 
	 * User can choose where to save file and what name to use
	 * @param tokensFinal: array of tokens
	 * @param countFinal: array of number of tokens
	 */
	
	public static void OutputTokens(String [] tokensFinal, int [] countFinal) {
		File OutFile = null;
		JFileChooser chooser2 = new JFileChooser();
		if (chooser2.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			OutFile = chooser2.getSelectedFile();
		}
		else {
			System.out.println("Please select an input file");
		}
		
		try (PrintWriter outFile = new PrintWriter(OutFile.getPath())){
		
			//print 2 parallel arrays
			for (int i=0; i < tokensFinal.length; i++) {
				outFile.printf("%s,%d\r\n", tokensFinal[i], countFinal[i]);
			}
			
			outFile.close();
		}
		catch(java.io.FileNotFoundException e) {
			System.out.println("You need to save a txt file!");
		}
	}

}

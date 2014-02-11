import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class SimlishParser {
	File simlishFile;
	File reservedWords = new File("reservedWords.txt"); //list of keywords
	File symbolTable = new File("symbolTable"); //list of used variables
	
	private int classifyWord(String text) {
		boolean isDigit = "1234567890".indexOf( text ) != -1;
		if ( isDigit )
		   	return 1;
		else if ( "qwrtyuiopasdfghjklzxcvbnmQWRTYUIOPASDFGHJKLZXCVBNM".indexOf( text ) != -1 )
		   	return 2;
		else if ( text.equals(":" ))
		   	return 1;
		else if ( text.equals(",") )
			return 2;
		else if ( text.equals("[") )
			return 3;
		else if ( text.equals("]") )
			return 4;
		else if ( text.equals("\"") )
			return 5;
		else if ( text.equals("(") )
			return 7;
		else if ( text.equals(")") )
			return 8;
		else if ( text.charAt(0) == '\r' || text.charAt(0) == '\n' 
			||  text.charAt(0) == '\t' || text.charAt(0) == '\b' )
		   	return 0; 
		else { //characters that aren't in the alphabet
			return 20;
		}
	}
	
	private int nextState[][] = {
			{ 99, 99, 1, 99, 99, 99, 99, 99, 99 }, //q0
			{ 2, 98, 99,  98, 98, 98, 98, 99, 99 }, //q1
			{ 99, 98, 99, 99, 99, 99, 99, 3, 99 }, //q2
			{ 99, 99, 99, 4, 5, 6, 7, 99, 99 }, //q3
			{ 99, 99, 99, 99, 99, 99, 99, 99, 8 }, //q4
			{ 99, 99, 99, 99, 99, 99, 99, 99, 8 }, //q5
			{ 99, 99, 99, 99, 99, 99, 99, 99, 8 }, //q6
			{ 99, 99, 99, 99, 99, 99, 99, 99, 8 }, //q7
	};
	
	public void setFile(File file) {
		simlishFile = file;
	}
	
	public boolean isKeyword(String word) {
		//read and write stuff to files
		return false;
	}
	
	public void parse() throws Exception{
		int currentState = 0;
		int prevState = 0;
		int input = 0;
		BufferedReader butt = null;
		try {
			butt = new BufferedReader(new FileReader(simlishFile));
			
			while ( (input = butt.read()) != -1 ) {
				char c = (char) input;
				int inputIndex = classifyWord(""+c);
				prevState = currentState;
				currentState = nextState[currentState][inputIndex];
				
				String word = "";
				
				switch ( currentState ) {
					case 0:
					break;
					case 1: //lookup words
						word += c;
					break;
					case 2:
					break;
					case 3:
					break;
					case 4:
					break;
					case 5:
					break;
					case 6:
					break;
					case 7:
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( butt != null )
				butt.close();
		}
	}
}

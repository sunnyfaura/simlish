import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimlishTokenizer {
	File reservedWords = new File("reservedWords.txt"); //list of keywords	
	LinkedList<Token> tokenList = new LinkedList<Token>(); 
	LinkedList<Info> infoList = new LinkedList<Info>(); 
	
	public SimlishTokenizer() throws Exception {
		BufferedReader reservedWordsRead = null;
		try {
			reservedWordsRead = new BufferedReader(new FileReader(reservedWords));
			String word = "";
			while( (word = reservedWordsRead.readLine()) != null ) {
				String[] k = word.split("==");
					infoList.add( new Info( Pattern.compile("^("+k[0]+")" ), //lexeme
							k[1] ) ); //token
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( reservedWordsRead != null )
				reservedWordsRead.close();
		}
	}
	
	public void tokenize(String str) {
		String s = str.trim();
		tokenList.clear();
			
		while (!s.equals("")) {
			boolean match = false;
			for ( Info thing: infoList ) {
				Matcher m = thing.pattern.matcher(s);
				if (m.find()) {
					match = true;
					String tok = m.group().trim();
					s = m.replaceFirst("").trim();
					tokenList.add( new Token( tok, thing.token ) );
					break;
				}
			} if (!match) {
				throw new ParserException("Unexpected character in input: "+s);
			}
		}
	}
	
	public LinkedList<Token> getTokens() {
		return tokenList;
	}
}

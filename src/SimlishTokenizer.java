import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimlishTokenizer {
	File reservedWords = new File("reservedWords.txt"); //list of keywords
	HashMap<String,String> tokenList = new HashMap<String,String>(); 
	HashMap<Pattern,String> infoList = new HashMap<Pattern,String>(); 
	
	public SimlishTokenizer() throws Exception {
		BufferedReader reservedWordsRead = null;
		try {
			reservedWordsRead = new BufferedReader(new FileReader(reservedWords));
			String word = "";
			while( (word = reservedWordsRead.readLine()) != null ) {
				String[] k = word.split(" ");
					infoList.put(Pattern.compile("^("+k[0]+")" ), //lexeme
							k[1]); //token
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
			for ( Entry<Pattern,String> thing: infoList.entrySet() ) {
				System.out.println("pattern: "+thing.getKey().toString()+", token: "+thing.getValue());
				Matcher m = thing.getKey().matcher(s);
				if (m.find()) {
					match = true;
					String tok = m.group().trim();
					s = m.replaceFirst("").trim();
					tokenList.put(tok,thing.getValue());
					break;
				}
			} if (!match) 
				throw new ParserException("Unexpected character in input: "+s);
		}
	}
	
	public HashMap<String,String> getTokens() {
		return tokenList;
	}
}

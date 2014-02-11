import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;


public class SimlishParser {
	File simlishFile;
	
	//set the Simlish file
	public void setFile(File file) {
		simlishFile = file;
	}

	//try a recursive descent parser
	public void parse() throws Exception {

		BufferedReader butt = null;
		try {
			butt = new BufferedReader(new FileReader(simlishFile));
			int input = 0;
			String temp = "";
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( butt != null )
				butt.close();
		}
	}
}

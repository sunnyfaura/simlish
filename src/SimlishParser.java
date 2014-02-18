import java.util.LinkedList;


public class SimlishParser {
	LinkedList<Token> simlish;
	Token first;

	public void nextToken() {
		simlish.pop();
		if(simlish.isEmpty())
			first = new simlish(simlish.EPSILON, "", -1);
		else
			first = simlish.getFirst();		
	}

	public void parse(LinkedList<Token> list) {
		simlish = list;
		first = simlish.getFirst();
		//call start of grammarz
	}
	
}

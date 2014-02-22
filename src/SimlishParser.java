import java.util.LinkedList;


public class SimlishParser {
	LinkedList<Token> simlish;
	Token first;
	//Token temp;

	public void nextToken() {
		simlish.pop();
		if(simlish.isEmpty())
			first = new Token ("eps", "eps");
		else
			first = simlish.getFirst();		
	}

	public void parse(LinkedList<Token> list) {
		simlish = list;
		first = simlish.getFirst();
		//call start of grammarz
		program();
	}

	//grammars
	//PROGRAM
	public void program() {
		if(first.equals("program_init")) {
			nextToken();
			init();
			if(first.equals("program_terminator")) {
				//program terminates
			}
			else {
				throw new ParserException("Program did not terminate properly.");
			}
		} else {
			throw new ParserException("Did you initialize the program? (Trying writing SULSUL)");
		}
	}
	public void init() {
		var();
		Q();
	}
	
	public void Q() {
		arr();
		QQ();
	}
	
	public void QQ() {
		program_main();
	}
	public void program_main() {
		
		
	}

	public void var() {
		variable_declaration();
	}
	public void arr() {	
		array_declaration();
	}
	public void variable_declaration()
	{
		if(first.equals("var_init"))
		{
			nextToken();
			if(first.equals(":"))
			{
				nextToken();
				variable();
				if(first.equals("block_terminator"))
				{
					System.out.println("ERROR: CREATE A SIM wasn't terminated properly.");
				}
				else
				{
					
				}
			}
		}
		//create an else-if for if epsilon
		//create an else it for array
		else
		{
			System.out.println("ERROR: CREATE A SIM wasn't started properly.");
		}
	}
	public void array_declaration()
	{

	}

	//VARIABLE DECLARATION
	public void variable()
	{
		T();
		variable();
	}
	public void T()
	{
		U();
		V();
		//if for period
	}
	public void U()
	{
		//if identifier
			//call data_type();
	}
	public void V()
	{
		
	}
	public void data_type()
	{}
	public void data_type_op()
	{}
	public void stuff()
	{}

	//ARRAY
	
	public void array()
	{}
	public void R()
	{}
	public void L()
	{}
	public void X()
	{}
	public void Y()
	{}
	
	
}

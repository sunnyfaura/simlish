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
		program();
	}

	public void parse(LinkedList<Token> list) {
		simlish = list;
		first = simlish.getFirst();
		//call start of grammarz
	}

	//grammars
	//PROGRAM
	public void program()
	{
		if(first.equals("program_init"))
		{
			nextToken();
			init();
			program_main();
			if(first.equals("program_terminator")) //unsure if it's supposed to be here
			{
				
			}
			else
			{
				System.out.println("ERROR: Program wasn't terminated properly.");
			}
		}
		else
		{
			System.out.println("ERROR: Program wasn't started properly.");
		}
	}
	public void init()
	{
		var();
		arr();
	}
	public void program_main()
	{
		
	}
	
	public void var()
	{
		variable_declaration();
		//OR EPSILON
	}
	public void arr()
	{	
		array_declaration();
		//OR EPSILON
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
	
}

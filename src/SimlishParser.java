import java.util.LinkedList;


public class SimlishParser {
	LinkedList<Token> lookupTable;
	LinkedList<Symbol> symbolTable;
	Token first;
	Symbol top;

	public void nextToken() {
		lookupTable.pop();
		if( lookupTable.isEmpty() )
			first = new Token( "EOF","EPSILON" );
		else
			first = lookupTable.getFirst();	
	}

	public void parse(LinkedList<Token> list) {
		lookupTable = list;
		symbolTable = new LinkedList<Symbol>();
		first = lookupTable.getFirst();
		program();
	}

	public void program() {
		if(first.token.equals("PROGRAM_INIT")) {
			nextToken();
			init();
			program_main();
			if(first.token.equals("PROGRAM_TERMINATOR")) {
				
			}
			else {
				System.out.println("ERROR: Program wasn't terminated properly.");
			}
		}
		else {
			System.out.println("ERROR: Program wasn't started properly.");
		}
	}
	public void init() {
		variable_declaration();

		array_declaration();
	}
	public void program_main() {
		//main
	}
	
	public void variable_declaration()
	{
		if(first.token.equals("VAR_INIT")) {
			System.out.println(first.token);
			nextToken();
			System.out.println(first.token);
			if(first.token.equals("COLON")) {
				variable();
				if(first.token.equals("BLOCK_TERMINATOR")) {
					System.out.println("CREATED SIMS SUCCESSFULLY!");
				}
				else {
					throw new ParserException("ERROR: CREATE A SIM wasn't terminated properly.");
				}
			}
		}
		else {
			throw new ParserException("Invalid declaration of variables.");
		}
	}

	public void variable() {
		T();
		variable();
	}
	
	public void T() {
		U();
		V();
		nextToken();
		if ( first.token.equals("PERIOD") ) {
			System.out.println("A successful variable declaration!");
		} else {
			throw new ParserException("Missing PERIOD for a variable declaration.");
		}
	}
	
	
	public void U() {
		nextToken();
		String name = "";
		System.out.println(first.token);
		if ( first.token.equals("IDENTIFIER") ) {
			if ( symbolTable.contains(name) ) {
				throw new ParserException( first.lexeme+" already exists." );
			} else {
				name = first.lexeme;
			}
		}
		System.out.println(name);
		nextToken();
		System.out.println(first.token);
		if( first.token.equals("LPAREN") ) {
			nextToken();
			System.out.println(first.token);
			if ( first.token.equals("DATA_TYPE") ) {
				symbolTable.add( new Symbol( name, first.lexeme ) );
				//throws a data type exception if invalid
				System.out.println("Name:"+name+", Data Type: "+first.lexeme);
			} else {
				throw new ParserException(first.lexeme+" is an invalid data type.");
			}
			nextToken();
			System.out.println(first.token);
			if ( first.token.equals("RPAREN") ) {
				System.out.println("Successful U");
			} else {
				throw new ParserException("Missing RPAREN where a \""+first.lexeme+"\" is declared.");
			}
		} else {
			throw new ParserException("Missing LPAREN where a \""+first.lexeme+"\" is declared.");
		}
	}

	
	public void V() {		
		nextToken();
		if( first.token.equals("STRING_ASGN") ) {
			System.out.println("Ok doing a String assignment.");
		} else if ( first.token.equals("FLOAT_ASGN") ) {
			System.out.println("Ok doing a Float assignment.");
		} else if ( first.token.equals("BOOL_ASGN") ) {
			System.out.println("Ok doing a Boolean assignment.");
		} else if ( first.token.equals("INT_ASGN") ) {
			System.out.println("Ok doing an Integer assignment.");
		} else {
			throw new ParserException(first.lexeme+" is not an assignment operator.");
		}
		
		nextToken();
		if( first.token.equals("DQUOTE") ) {
			assignment();
		} else {
			throw new ParserException("Missing terminating DQUOTE for an assignment operation.");
		}
	}
	
	String value = "";
	
	public void assignment() {
		//I'm trying to make the thing get everything inside the DQUOTES and insert it as a new Symbol
		top = symbolTable.peek();
		nextToken();
		if ( first.token.equals("DQUOTE") ) {
			System.out.println("Correct syntax for assignment");
		} else {
			throw new ParserException("Missing starting DQUOTE for an assignment operation.");
		}
	}
	
	public void array_declaration() {

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

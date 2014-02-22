import java.util.LinkedList;


public class SimlishParser {
	LinkedList<Token> lookupTable;
	LinkedList<Symbol> symbolTable;
	Token first;
	Symbol top;
	String output;
	String identifier;

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
	
	public void setOutput(String output) {
		this.output = output;
	}
	
	public String getOutput() {
		return output;
	}

	public void program() {
		System.out.println("program init:"+first.token);
		if(first.token.equals("PROGRAM_INIT")) {
			init();
			program_main();
			nextToken();
			if(first.token.equals("PROGRAM_TERMINATOR")) {
				System.out.println("Thank you for playing Sims!");
			} else {
				throw new ParserException("Program wasn't terminated properly.");
			}
		}
		else {
			throw new ParserException("Program wasn't initialized properly.");
		}
	}
		
	public void init() {
		nextToken();
		if ( first.token.equals("VAR_INIT") ) {
			variable_declaration();
			nextToken();
			if ( first.token.equals("ARRAY_INIT") ) {
				array_declaration();
			} else {
				//do nothing
			}
		} else if ( first.token.equals("ARRAY_INIT") ) {
			array_declaration();
		} else {
			//do nothing
			nextToken(); //to call program_main in case no variables/array declared
		}
	}
	
	public void program_main() {
		if(first.token.equals("PROGRAM_MAIN"))
		{
			nextToken();
			G();
			if(first.token.equals("BLOCK_TERMINATOR"))
			{
				System.out.println("Thank you for playing Sims!");
			}
			else
			{
				System.out.println("Error: LIVE MODE wasn't terminated properly");
			}
			
		}
		else
		{
			System.out.println("Error: LIVE MODE wasn't terminated started");
		}
	}
	
	public void variable_declaration() {
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

	public void variable() {
		T();
		variable();
	}
	
	public void T() {
		U();
		if ( first.token.equals("PERIOD") ) {
			System.out.println("A successful variable declaration!");
		} else {
			throw new ParserException("T: Missing PERIOD for a variable declaration.");
		}
	}
	
	public void U() {
		nextToken();
		System.out.println("U identifier "+first.token);
		if ( first.token.equals("IDENTIFIER") ) {
			identifier = first.lexeme;
			nextToken();
			System.out.println(first.token);
			if ( first.token.equals("LPAREN") ) {
				V();
			} else {
				throw new ParserException("Missing LPAREN for \""+identifier+"\"");
			}
		}
	}
	
	public void V() {
		nextToken();
		System.out.println("data type: "+first.token);
		if ( first.token.equals("INT_TYPE") ) {
			int_declaration();
		} else if ( first.token.equals("FLOAT_TYPE") ) {
			float_declaration();
		} else if ( first.token.equals("STRING_TYPE") ) {
			string_declaration();
		} else if ( first.token.equals("BOOL_TYPE") ) {
			bool_declaration();
		} else {
			throw new ParserException("\""+first.token+"\" is not a valid data type.");
		}
	}
	
	public void int_declaration(){
		nextToken();
		System.out.println("rparen int: "+first.token);
		if ( first.token.equals("RPAREN") ) {
			nextToken();
			System.out.println("asgn int: "+first.token);
			symbolTable.add(new Symbol(identifier));
			if ( first.token.equals("INT_ASGN") ) {
				int_assignment();
			} else {
				//do nothing
			}
		} else {
			throw new ParserException("Missing RPAREN for \""+identifier+"\"");
		}
	} 
	
	public void float_declaration(){
		nextToken();
		System.out.println("rparen float: "+first.token);
		if ( first.token.equals("RPAREN") ) {
			nextToken();
			System.out.println("asgn float: "+first.token);
			symbolTable.add(new Symbol(identifier));
			if ( first.token.equals("FLOAT_ASGN") ) {
				float_assignment();
			} else {
				//do nothing
			}
		} else {
			throw new ParserException("Missing RPAREN for \""+identifier+"\"");
		}
	} 
	
	public void string_declaration(){
		nextToken();
		System.out.println("rparen string: "+first.token);
		if ( first.token.equals("RPAREN") ) {
			nextToken();
			System.out.println("asgn string: "+first.token);
			symbolTable.add(new Symbol(identifier));
			if ( first.token.equals("STRING_ASGN") ) {
				string_assignment();
			} else {
				// do nothing
			}
		} else {
			throw new ParserException("Missing RPAREN for \""+identifier+"\"");
		}
	}
	
	public void bool_declaration(){
		nextToken();
		System.out.println("rparen bool: "+first.token);
		if ( first.token.equals("RPAREN") ) {
			nextToken();
			System.out.println("asgn bool"+first.token);
			symbolTable.add(new Symbol(identifier));
			if ( first.token.equals("BOOL_ASGN") ) {
				bool_assignment();
			} else {
				//do nothing
			}
		} else {
			throw new ParserException("Missing RPAREN for \""+identifier+"\"");
		}
	}
	
	public void int_assignment(){
		nextToken();
		System.out.println(first.token);
		Symbol pot = symbolTable.peekLast();
		System.out.println("Identifier: "+pot.identifier);
		if( first.token.equals("NUMBER") ) {
			pot.assignInt(first.lexeme);
			nextToken();
			System.out.println(first.token);
			//call next token to know if next symbol is PERIOD
		} else {
			throw new ParserException("Cannot assign "+first.lexeme+" to "+top.identifier);
		}
	}
	
	public void float_assignment(){
		nextToken();
		System.out.println(first.token);
		Symbol pot = symbolTable.peekLast();
		System.out.println("Identifier: "+pot.identifier);
		if( first.token.equals("NUMBER") ) {
			pot.assignFloat(first.lexeme);
			nextToken();
			System.out.println(first.token);
			//call next token to know if next symbol is PERIOD
		} else {
			throw new ParserException("Cannot assign "+first.lexeme+" to "+top.identifier);
		}
	}
	
	public void string_assignment(){
		nextToken();
		System.out.println(first.token);
		Symbol pot = symbolTable.peekLast();
		System.out.println("Identifier: "+pot.identifier);
		if( first.token.equals("STRING_LITERAL") ) {
			pot.assignString(first.lexeme);
			nextToken();
			System.out.println(first.token);
			//call next token to know if next symbol is PERIOD
		} else {
			throw new ParserException("Cannot assign "+first.lexeme+" to "+top.identifier);
		}
	}
	
	public void bool_assignment() {
		nextToken();
		System.out.println(first.token);
		Symbol pot = symbolTable.peekLast();
		System.out.println("Identifier: "+pot.identifier);
		if( first.token.equals("BOOL_LITERAL") ) {
			pot.assignBool(first.lexeme);
			nextToken();
			System.out.println(first.token);
			//call next token to know if next symbol is PERIOD
		} else {
			throw new ParserException("Cannot assign "+first.lexeme+" to "+top.identifier);
		}
	}
	
	public void array_declaration() {

	}
	
	//PROGRAM MAIN
	public void G()
	{
		print();
	}
	
	public void print()
	{
		if(first.token.equals("PRINT_OP"))
		{
			nextToken();
			print_stuff();
		}
		else
		{
		}
	}
	
	public void expression()
	{
		term();
		sum_diff_op();
	}
	
	public void sum_diff_op() {
		// TODO Auto-generated method stub
		sum_diff();
		term();
		sum_diff_op();
		
	}

	public void sum_diff() {
		// TODO Auto-generated method stub
		if(first.token.equals("ADD_OP"))
		{
			
		}
		else if(first.token.equals("SUB_OP"))
		{
			
		}
	}

	public void term() {
		factor();
		mult_div_op();
		
	}

	public void mult_div_op() {
		// TODO Auto-generated method stub
		mult_div();
		factor();
		mult_div_op();
	}

	

	public void mult_div() {
		// TODO Auto-generated method stub
		if(first.token.equals("MULT_OP"))
		{
			
		}
		else if(first.token.equals("DIV_OP"))
		{
			
		}
		else if(first.token.equals("MOD_OP"))
		{
			
		}
		
		
	}

	public void factor() {
		// TODO Auto-generated method stub
		argument();
		exponent();
	}

	public void exponent() {
		// TODO Auto-generated method stub
		if(first.token.equals("EXP_OP"))
		{
			nextToken();
			argument();
			exponent();
		}
		else
		{}
		
	}

	public void argument() {
		// TODO Auto-generated method stub
		value();
		expression();
	}

	public void value() {
		// TODO Auto-generated method stub
		if(first.token.equals("NUMBER"))
		{
			nextToken();
		}
		else
		{}
		
	}

	public void print_stuff()
	{
		X();
	}
	
	public void X()
	{
		if(first.token.equals("IDENTIFIER"))
		{
			//look up lexeme at identifier
			//System.out.println(first.lexeme);
		}
		else
		{
			nextToken();
			literal();
		}
	}
	
	public void literal()
	{
		if(first.token.equals("#"))
		{
			nextToken();
			
			if(first.token.equals("STRING_LITERAL"))
			{
				int tempValue = first.lexeme.length()-1;
				String printTemp = first.lexeme.substring(0, tempValue);
				System.out.println(printTemp);
				
			}
			else
			{
				System.out.println("Missing closing #");
			}
		}
		else if(first.token.equals("["))
		{
			nextToken();
			if(first.token.equals("NUMERIC_LITERAL"))
			{
				 String printTemp = first.lexeme;
				 nextToken();
				 if(first.token.equals("]"))
				 {
					 System.out.println(printTemp);
				 }
				 else
				 {
					 System.out.println("Missing closing ]");
				 }
				 
			}
			else if(first.token.equals("BOOL_LITERAL"))
			{
				String printTemp = first.lexeme;
				 nextToken();
				 if(first.token.equals("]"))
				 {
					 System.out.println(printTemp);
				 }
				 else
				 {
					 System.out.println("Missing closing ]");
				 }
			}
			else
			{
				System.out.println("ERROR: Number or Gender expected");
			}
			
			
		}
		else
		{
			expression();
		}
	}
	
	
	
}

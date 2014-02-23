import java.util.ArrayList;
import java.util.LinkedList;


public class SimlishParser {
	LinkedList<Token> lookupTable;
	LinkedList<Symbol> symbolTable;
	ArrayList<Element> tparty = new ArrayList<Element>();
	ArrayList<ArrayList<Element>> party;
	Token first;
	Symbol top;
	String output = "";
	String identifier;

	public void nextToken() {
		lookupTable.pop();
		if( lookupTable.isEmpty() )
			first = new Token( "EOF","EPSILON" );
		else
			first = lookupTable.getFirst();	
	}
	
	public Symbol findSymbol( String identifier ) {
		Symbol ret = null;
		for( Symbol s : symbolTable ) {
			if ( s.identifier.equals(identifier) ) {
				ret = s;
			}
		}
		return ret;
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
		System.out.println("Init: "+first.token);
		if ( first.token.equals("VAR_INIT") ) {
			variable_declaration();
			nextToken();
			System.out.println("Array time! "+first.token);
			if ( first.token.equals("ARRAY_INIT") ) {
				array_declaration();
			} else {
				//do nothing
				//goes to program_main
			}
		} else if ( first.token.equals("ARRAY_INIT") ) {
			array_declaration();
		} else {
			//do nothing
//			nextToken(); //to call program_main in case no variables/array declared
		}
	}
	
	public void variable_declaration() {
		System.out.println(first.token);
		nextToken();
		System.out.println(first.token);
		if(first.token.equals("COLON")) {
			variable();
		}
	}

	public void variable() {
		T();
		if ( first.token.equals("BLOCK_TERMINATOR") ) {
			System.out.println("Successfully created Sims! Double check");
		} else {
			variable();
		}		
	}
	
	public void T() {
		nextToken();
		System.out.println("T identifier "+first.token);
		if ( first.token.equals("BLOCK_TERMINATOR") ) {
			System.out.println("Successfully created Sims!");
		} else {
			U();
			if ( first.token.equals("PERIOD") ) {
				System.out.println("A successful variable declaration!");
				
			} else {
				throw new ParserException("Missing PERIOD for a variable declaration.");
			}
		}
	}
	
	public void U() {
		System.out.println("U identifier "+first.token);
		if ( first.token.equals("IDENTIFIER") ) {
			identifier = first.lexeme;
			if ( findSymbol(identifier) != null ) {
				throw new ParserException("\""+identifier+"\" already exists! Choose a different variable name.");
			} else {
				nextToken();
				System.out.println(first.token);
				if ( first.token.equals("LPAREN") ) {
					V();
				} else {
					throw new ParserException("Missing LPAREN for \""+identifier+"\"");
				}
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
			pot.assignString(  (first.lexeme).replaceAll("#","")  );
			System.out.println(pot.stringVal);
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
		if(first.token.equals("ARRAY_INIT"))
		{
			nextToken();
			if(first.token.equals("COLON"))
			{
				nextToken();
				array();
			}
			else
			{
				throw new ParserException("Missing COLON");
			}
		}
		else
		{}
	}
	
	private void array() {
		// TODO Auto-generated method stub
		if(first.token.equals("IDENTIFIER"))
		{
			Symbol symbol = findSymbol(first.lexeme);
			if ( symbol == null ) {
				Symbol temp =  new Symbol(first.lexeme);
				symbolTable.add(temp);
			}
			else
			{
				throw new ParserException("\""+first.lexeme+"\" has been declared before!");
			}
			nextToken();
			R();
			if(first.token.equals("PERIOD"))
			{
				nextToken();
				party.add(tparty);
				tparty=new ArrayList<Element>();
				array();
			}
		}
		else if(first.token.equals("BLOCK_TERMINATOR"))
		{
			nextToken();
			
		}
		else
		{
			throw new ParserException("THROW A PARTY wasn't terminated properly");
		}
	}

	private void R() {
		// TODO Auto-generated method stub
		if(first.token.equals("ARRARY_ASGN"))
		{
			nextToken();
			if(first.token.equals("LBRACKET"))
			{
				nextToken();
				array_element();
				array_op();
				if(first.token.equals("RBRACKET"))
				{
					
				}
			}
		}
	}

	private void array_op() {
		// TODO Auto-generated method stub
		if(first.token.equals("COMMA"))
		{
			nextToken();
			array_element();
			array_op();
		}
		else
		{
			
		}
	}

	public void array_element() {
		// TODO Auto-generated method stub
		if( first.token.equals("IDENTIFIER") ) {
			Symbol symbol = findSymbol(first.lexeme);
			if ( symbol == null ) {
				throw new ParserException("\""+first.lexeme+"\" was not declared!");
			} else {
				nextToken();
				Element temp = new Element();
				temp.setSymbol(symbol);
				temp.setDataType("SYMBOL");
				tparty.add(temp);
			}			
		}
		else if(first.token.equals("STRING_LITERAL"))
		{
			nextToken();
			Element temp = new Element();
			temp.setHuman(first.lexeme);
			temp.setDataType("HUMAN");
			tparty.add(temp);
		}
		else if(first.token.equals("INT_LITERAL"))
		{
			nextToken();
			Element temp = new Element();
			temp.setPet(Integer.parseInt(first.lexeme));
			temp.setDataType("PET");
			tparty.add(temp);
		}
		else if(first.token.equals("FLOAT_LITERAL"))
		{
			
			nextToken();
			Element temp = new Element();
			temp.setSupernatural(Float.parseFloat(first.lexeme));
			temp.setDataType("SUPERNATURAL");
			tparty.add(temp);
		}
		else if(first.token.equals("BOOL_LITERAL"))
		{
			nextToken();
			Element temp = new Element();
			temp.setGender(first.lexeme);
			temp.setDataType("GENDER");
			tparty.add(temp);
		}
		
	}
	//party.add(tParty); call at the end

	public void program_main() {
		System.out.println("Main: "+first.token);
		if(first.token.equals("MAIN_INIT")) {
			nextToken();
			System.out.println("G: "+first.token);
			G();
			
			System.out.println("Terminating: "+first.token);
			if(first.token.equals("BLOCK_TERMINATOR")) {
				System.out.println("Live Mode successful!");
			}
			else {
				throw new ParserException("LIVE MODE wasn't terminated.");
			}
		}
		else {
			throw new ParserException("LIVE MODE wasn't initialized.");
		}
	}
	
	public void G() {
		nextToken();
		System.out.println("H: "+first.token);
		if ( first.token.equals("BLOCK_TERMINATOR") ) {
			//do nothing
		} else {
			H();
			G();
		}
	}
	
	String literal = "";
	
	public void H() {
		if ( first.token.equals("PRINT_OP") ) {
			//print
			print();
		} else if ( first.token.equals("IDENTIFIER") ) {
			//variable or array assignment
		} else if ( first.token.equals("NUMBER") ) {
			//int or float expr
		} else if ( first.token.equals("STRING_LITERAL") ) {
			string_expr(false);
			
			nextToken();
			if( first.token.equals("PERIOD") ) {
				System.out.println("Successfully concatenated strings!");
			} else {
				throw new ParserException("Missing PERIOD for a string concatenation statement.");
			}
		} else if ( first.token.equals("BOOL_LITERAL") ) {
			//bool expr
		} else if ( first.token.equals("ITERATOR")) {
			//iteration
		} else if ( first.token.equals("IF")) {
			//conditional
		} else {
			throw new ParserException("Invalid statement in LIVE MODE.");
		}
	}
	
	public void string_term() {
		nextToken();
		System.out.println("literal to concatenate: "+first.lexeme);
		if( first.token.equals("STRING_LITERAL") ) {
			literal += first.lexeme;
		} else {
			//do nothing
		}
	}
	
	public void string_expr(boolean print) {
		System.out.println("literal to concatenate: "+first.lexeme);
		literal += first.lexeme;
		string_term();			
		literal = literal.replaceAll("#", "");
		System.out.println("literal msg: "+literal);
		if (print) output += literal+"\n";
		System.out.println("INTERPRETER: "+output);
		literal = "";
	}
	
	public void print() {
		if(first.token.equals("PRINT_OP")) {
			print_stuff();
		}
	}
	
	public void print_stuff() {
		X();
		if( first.token.equals("PERIOD") ) {
			System.out.println("Successfully printed strings!");
		} else {
			throw new ParserException("Missing PERIOD for a string concatenation statement.");
		}
	}
	
	public void X() {
		nextToken();
		System.out.println("X: "+first.token);
		if ( first.token.equals("IDENTIFIER") ) {
			//variable or array assignment
		} else if ( first.token.equals("NUMBER") ) {
			//int or float expr
		} else if ( first.token.equals("STRING_LITERAL") ) {
			string_expr(true);
		} else if ( first.token.equals("BOOL_LITERAL") ) {
			//bool expr
		}
	}
	
	public void expression() {
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
		if(first.token.equals("ADD_OP")) {
			
		}
		else if(first.token.equals("SUB_OP")) {
			
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
		if(first.token.equals("MULT_OP")) {
			
		}
		else if(first.token.equals("DIV_OP")) {
			
		}
		else if(first.token.equals("MOD_OP")) {
			
		}
		
		
	}

	public void factor() {
		// TODO Auto-generated method stub
		argument();
		exponent();
	}

	public void exponent() {
		// TODO Auto-generated method stub
		if(first.token.equals("EXP_OP")) {
			nextToken();
			argument();
			exponent();
		}
		else {
			
		}
		
	}

	public void argument() {
		// TODO Auto-generated method stub
		value();
		expression();
	}

	public void value() {
		// TODO Auto-generated method stub
		if(first.token.equals("NUMBER")) {
			nextToken();
		}
		else {
			
		}
	}
}

//what

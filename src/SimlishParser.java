import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class SimlishParser {
	LinkedList<Token> lookupTable;
	LinkedList<Symbol> symbolTable;
	ArrayList<Element> tparty = new ArrayList<Element>();
	ArrayList<ArrayList<Element>> party = new ArrayList<ArrayList<Element>>();
	Token first;
	Symbol top;
	String output = "";
	String literal = "";
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
			symbolTable.add(new Symbol(identifier,"INTEGER"));
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
			symbolTable.add(new Symbol(identifier,"FLOAT"));
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
			symbolTable.add(new Symbol(identifier,"STRING"));
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
			symbolTable.add(new Symbol(identifier,"BOOL"));
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
			pot.setDataType("INT");
			nextToken();
			//pot = symbolTable.peekLast();
			
			System.out.println(first.token);
			System.out.println(pot.getDataType()+" "+pot.getIntVal());
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
			pot.setDataType("FLOAT");
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
			pot.setDataType("STRING");

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
			pot.setDataType("BOOL");
			nextToken();
			System.out.println(first.token);
			//call next token to know if next symbol is PERIOD
		} else {
			throw new ParserException("Cannot assign "+first.lexeme+" to "+top.identifier);
		}
	}
	
	public void array_declaration() {
		
		//
		if(first.token.equals("ARRAY_INIT"))
		{
			nextToken();
			
			if(first.token.equals("COLON"))
			{
				//System.out.println("I'M HERE");
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
	
	public void array() {
		// TODO Auto-generated method stub
		//
		//
		if(first.token.equals("IDENTIFIER"))
		{
			Symbol symbol = findSymbol(first.lexeme);
			if ( symbol == null ) {
				Symbol temp =  new Symbol(first.lexeme,"ARRAY");
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
			else
			{
				throw new ParserException("Missing PERIOD");
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

	public void R() {
		// TODO Auto-generated method stub
		if(first.token.equals("ARRAY_ASGN"))
		{
			nextToken();

			//
			if(first.token.equals("LBRACKET"))
			{
				nextToken();
				array_element();
				array_op();
				if(first.token.equals("RBRACKET"))
				{
					nextToken();
				}
			}
		}
	}

	public void array_op() {
		// TODO Auto-generated method stub
		if(first.token.equals("COMMA"))
		{
			nextToken();
			array_element();
			array_op();
		}
		else
		{
			//nextToken();
		}
	}

	public void array_element() {
		// TODO Auto-generated method stub
		if( first.token.equals("IDENTIFIER") ) {
			Symbol symbol = findSymbol(first.lexeme);
			if ( symbol == null ) {
				throw new ParserException("\""+first.lexeme+"\" was not declared!");
			} else {
				
				Element temp = new Element();
				temp.setSymbol(symbol);
				temp.setDataType("ARRAY");
				tparty.add(temp);
				nextToken();
			}			
		}
		else if(first.token.equals("STRING_LITERAL"))
		{
			
			Element temp = new Element();
			temp.setHuman(first.lexeme);
			temp.setDataType("STRING");
			tparty.add(temp);
			nextToken();
		}
		else if(first.token.equals("NUMBER"))
		{
			
			Element temp = new Element();
			temp.setPet(Integer.parseInt(first.lexeme));
			temp.setDataType("INT");
			tparty.add(temp);
			nextToken();
		}
		else if(first.token.equals("FLOAT_LITERAL"))
		{
			
			
			Element temp = new Element();
			temp.setSupernatural(Float.parseFloat(first.lexeme));
			temp.setDataType("FLOAT");
			tparty.add(temp);
			nextToken();
		}
		else if(first.token.equals("BOOL_LITERAL"))
		{
		
			Element temp = new Element();
			temp.setGender(first.lexeme);
			temp.setDataType("BOOL");
			tparty.add(temp);
			nextToken();
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
	
	public void H() {
		if ( first.token.equals("PRINT_OP") ) {
			//print
			print();
		} else if ( first.token.equals("IDENTIFIER") ) {
			//variable or array assignment
			Symbol temp = findSymbol(first.lexeme);
			if ( temp == null ) {
				throw new ParserException("\""+first.lexeme+"\" does not exist!");
			} else {
				System.out.println( temp.identifier+" data type: "+ temp.getDataType() );
				variable_assignment(temp);
			}
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
			Symbol symbol = findSymbol(first.lexeme);
			if ( symbol == null ) {
				throw new ParserException("\""+first.lexeme+"\" was not declared!");
			} else {
				int iTemp = 0;
				for(int i = 0; i < symbolTable.size();i++)
				{
					Symbol teaPot = symbolTable.get(i);
					String tPot = teaPot.getIdentifier();
					
					if(tPot.equals(first.lexeme))
					{
						iTemp = i;
						break;
					}
				}
				
				Symbol pot = symbolTable.get(iTemp);
				String tempDT = pot.getDataType();
				
				if(tempDT.equals("INT"))
				{
					output += pot.getIntVal() + " \n";
					
				}
				else if(tempDT.equals("FLOAT"))
				{
					output += pot.getFloatVal() + "\n";
				}
				else if(tempDT.equals("STRING"))
				{
					output += pot.getStringVal() + "\n";
				}
				else if(tempDT.equals("BOOL"))
				{
					output += pot.getBoolVal() + "\n";
				}
				nextToken();
			}	
			//variable or array assignment
		} else if ( first.token.equals("NUMBER") ) {
			//int or float expr
			//output += first.lexeme + "\n";
			
		} else if ( first.token.equals("STRING_LITERAL") ) {
			string_expr(true);
		} else if ( first.token.equals("BOOL_LITERAL") ) {
			//bool expr
			output += first.lexeme + "\n";
		}
	}
	
	public void variable_assignment(Symbol s) {
		nextToken();
		System.out.println("var_asgn: "+first.token);
		if ( s.dataType.equals("INTEGER") ) {
			if( first.token.equals("INT_ASGN") ) {
				nextToken();
				System.out.println("int expr: "+int_expr(first.lexeme) );
				System.out.println("int asgn period: "+first.token);
				if( first.token.equals("PERIOD") ) {
					System.out.println("Successfull assignment for "+s.identifier+".");
				} else {
					throw new ParserException("Missing PERIOD for "+s.identifier+".");
				}
			} else {
				throw new ParserException("Invalid assignment for "+s.identifier+".");
			}
		} else if ( s.dataType.equals("FLOAT") ) {
			if( first.token.equals("FLOAT_ASGN") ) {
				nextToken();
				System.out.println("float expr: "+float_expr(first.lexeme) );
				if( first.token.equals("PERIOD") ) {
					System.out.println("Successfull assignment for "+s.identifier+".");
				} else {
					throw new ParserException("Missing PERIOD for "+s.identifier+".");
				}
			} else {
				throw new ParserException("Invalid assignment for "+s.identifier+".");
			}
		} else if ( s.dataType.equals("STRING") ) {
			if( first.token.equals("STRING_ASGN") ) {
				string_expr(false);
				nextToken();
				if( first.token.equals("PERIOD") ) {
					System.out.println("Successfull assignment for "+s.identifier+".");
				} else {
					throw new ParserException("Missing PERIOD for "+s.identifier+".");
				}
			} else {
				throw new ParserException("Invalid assignment for "+s.identifier+".");
			}
		} else if ( s.dataType.equals("BOOL") ) {
			if( first.token.equals("BOOL_ASGN") ) {
				bool_expr();
				if( first.token.equals("PERIOD") ) {
					System.out.println("Successfull assignment for "+s.identifier+".");
				} else {
					throw new ParserException("Missing PERIOD for "+s.identifier+".");
				}
			} else {
				throw new ParserException("Invalid assignment for "+s.identifier+".");
			}
		}
	}

	public int int_expr(String thing) {
		int value = int_term(thing);
		return int_sum_diff_op(value);
	}
	
	public int int_term(String thing) {
		int value = int_factor(thing);
		return int_mult_div_op(value);
	}
	
	public int int_factor(String thing) {
		int value = int_argument(thing);
		return int_exponent(value);
	}
	
	public int int_argument(String thing) {
		int value;
		System.out.println("int args: "+first.lexeme);
		if( first.token.equals("NUMBER") ) {
			//something
			System.out.println("NUMBERS!!!!!!!");
			value = Integer.parseInt(first.lexeme);
			System.out.println("args value: "+value);
			nextToken(); //dunno to call PERIOD or something
			System.out.println("PERIOD??? "+first.token);
		} else {
			if ( first.token.equals("LPAREN") ) {
				nextToken();
				value = int_expr(first.lexeme);
				if( first.token.equals("RPAREN") ) {
					nextToken(); //for PERIOD or something
				} else {
					throw new ParserException("Missing RPAREN.");
				}
			} else {
				throw new ParserException("Cannot perform integer operation.");
			}
		}	
		return value;
	}
	
	public int int_sum_diff_op(int v) {
//		nextToken();
		int value = v;
		if ( first.token.equals("PERIOD") ) {
			System.out.println("whaaaaaaaaaaaaaaat sumdiff: "+value);
			return value;
		} else {
			System.out.println("sumdiff val: "+value);
			System.out.println("sumdiff: "+first.lexeme);
			if( first.token.equals("ADD_OP") ) {
				System.out.println("about to be married!");
				nextToken();
				System.out.println("marries: "+first.lexeme);
				value += int_term(first.lexeme); 
				value = int_sum_diff_op(value);
			} else if( first.token.equals("SUB_OP") ) {
				System.out.println("about to expire!");
				nextToken();
				System.out.println("expires: "+first.token);
				value -= int_term(first.lexeme);
				value = int_sum_diff_op(value);
			} else {
				//do nothing
			}
		}
		
		return value;
	}
	
	public int int_mult_div_op(int v) {
		// TODO Auto-generated method stub
		//	nextToken();
		int value = v;
		if ( first.token.equals("PERIOD") ) {
			System.out.println("whaaaaaaaaaaaaaaat multdivmod: "+value);
			return value;
		} else {
			System.out.println("multdiv: "+first.lexeme);
			if( first.token.equals("MULT_OP") ) {
				System.out.println("about to woohoo!");
				nextToken();
				System.out.println("woohoos: "+first.lexeme);
				value *= int_factor(first.lexeme);
				value = int_mult_div_op(value);
			} else if ( first.token.equals("DIV_OP") ) {
				System.out.println("about to divide!");
				nextToken();
				System.out.println("divides: "+first.lexeme);
				value /= int_factor(first.lexeme);
				value = int_mult_div_op(value);
			} else if ( first.token.equals("MOD_OP") ) {
				System.out.println("about to adopt!");
				nextToken();
				System.out.println("adopts: "+first.lexeme);
				value %= int_factor(first.lexeme);
				value = int_mult_div_op(value);
			} else {
				//do nothing
			}
		}
		
		return value;
	}
	
	public int int_exponent(int v) {
//		nextToken();
		int value = v;
		if ( first.token.equals("PERIOD") ) {
			System.out.println("whaaaaaaaaaaaaaaat exp: "+value);
			return value;
		} else {
			System.out.println("promoting: "+first.lexeme);
			if( first.token.equals("EXP_OP") ) {
				System.out.println("about to promote!");
				nextToken();
				System.out.println("promotes: "+first.lexeme);
				value = (int) Math.pow( value, int_argument(first.lexeme) ) ;
				value = int_exponent(value);
			} else {
				//do nothing
			}
		}	
			
		return value;
	}
	
	public float float_expr(String thing) {
		float value = float_term(thing);
		return float_sum_diff_op(value);
	}
	
	public float float_term(String thing) {
		float value = float_factor(thing);
		return float_mult_div_op(value);
	}
	
	public float float_factor(String thing) {
		float value = float_argument(thing);
		return float_exponent(value);
	}
	
	public float float_argument(String thing) {
		float value;
		System.out.println("float args: "+first.lexeme);
		if( first.token.equals("NUMBER") ) {
			//something
			System.out.println("NUMBERS!!!!!!!");
			value = Float.parseFloat(first.lexeme);
			System.out.println("args value: "+value);
			nextToken(); //dunno to call PERIOD or something
			System.out.println("PERIOD??? "+first.token);
		} else {
			if ( first.token.equals("LPAREN") ) {
				nextToken();
				value = float_expr(first.lexeme);
				if( first.token.equals("RPAREN") ) {
					nextToken(); //for PERIOD or something
				} else {
					throw new ParserException("Missing RPAREN.");
				}
			} else {
				throw new ParserException("Cannot perform float operation.");
			}
		}	
		return value;
	}
	
	public float float_sum_diff_op(float v) {
//		nextToken();
		float value = v;
		if ( first.token.equals("PERIOD") ) {
			System.out.println("whaaaaaaaaaaaaaaat sumdiff: "+value);
			return value;
		} else {
			System.out.println("sumdiff val: "+value);
			System.out.println("sumdiff: "+first.lexeme);
			if( first.token.equals("ADD_OP") ) {
				System.out.println("about to be married!");
				nextToken();
				System.out.println("marries: "+first.lexeme);
				value += float_term(first.lexeme); 
				value = float_sum_diff_op(value);
			} else if( first.token.equals("SUB_OP") ) {
				System.out.println("about to expire!");
				nextToken();
				System.out.println("expires: "+first.token);
				value -= float_term(first.lexeme);
				value = float_sum_diff_op(value);
			} else {
				//do nothing
			}
		}
		
		return value;
	}
	
	public float float_mult_div_op(float v) {
		// TODO Auto-generated method stub
		//	nextToken();
		float value = v;
		if ( first.token.equals("PERIOD") ) {
			System.out.println("whaaaaaaaaaaaaaaat multdivmod: "+value);
			return value;
		} else {
			System.out.println("multdiv: "+first.lexeme);
			if( first.token.equals("MULT_OP") ) {
				System.out.println("about to woohoo!");
				nextToken();
				System.out.println("woohoos: "+first.lexeme);
				value *= float_factor(first.lexeme);
				value = float_mult_div_op(value);
			} else if ( first.token.equals("DIV_OP") ) {
				System.out.println("about to divide!");
				nextToken();
				System.out.println("divides: "+first.lexeme);
				value /= float_factor(first.lexeme);
				value = float_mult_div_op(value);
			} else if ( first.token.equals("MOD_OP") ) {
				System.out.println("about to adopt!");
				nextToken();
				System.out.println("adopts: "+first.lexeme);
				value %= float_factor(first.lexeme);
				value = float_mult_div_op(value);
			} else {
				//do nothing
			}
		}
		
		return value;
	}
	
	public float float_exponent(float v) {
//		nextToken();
		float value = v;
		if ( first.token.equals("PERIOD") ) {
			System.out.println("whaaaaaaaaaaaaaaat exp: "+value);
			return value;
		} else {
			System.out.println("promoting: "+first.lexeme);
			if( first.token.equals("EXP_OP") ) {
				System.out.println("about to promote!");
				nextToken();
				System.out.println("promotes: "+first.lexeme);
				value = (float) Math.pow( value, float_argument(first.lexeme) ) ;
				value = float_exponent(value);
			} else {
				//do nothing
			}
		}	
			
		return value;
	}
	
	public void bool_expr() {
		
	}
}
 

public class Symbol {
	String identifier;
	int intVal;
	double doubleVal;
	String stringVal, boolVal;
	
	public Symbol( String identifier, String dataType, String value) {
		this.identifier = identifier;
		if ( dataType.equals("human") )
			stringVal = value;
		else if ( dataType.equals("pet") )
			intVal = Integer.parseInt( value );
		else if ( dataType.equals("supernatural") )
			//check if valid float
			doubleVal = Double.parseDouble(value);
		else if ( dataType.equals("gender") && ( value.equals("male") || value.equals("female")  ) )
			boolVal =  value;
		else throw new DataTypeException( dataType + " is an invalid data type." );
	}
}


public class Symbol {
	public String identifier;
	public String dataType;
	public int intVal;
	public double doubleVal;
	public String stringVal, boolVal;
	
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
	
	public Symbol( String identifier, String dataType ) {
		this.identifier = identifier;
		this.dataType = dataType;
	}
	
	public void assign( String value ){
		if ( dataType.equals("human") )
			stringVal = value;
		else if ( dataType.equals("pet") )
			intVal = Integer.parseInt( value );
		else if ( dataType.equals("supernatural") )
			//check if valid float
			doubleVal = Double.parseDouble(value);
		else if ( dataType.equals("gender") && ( value.equals("male") || value.equals("female")  ) )
			boolVal =  value;
		else throw new DataTypeException( identifier+" is of "+dataType+" data type." );
	}
}

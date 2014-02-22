
public class Symbol {
	public String identifier;
	public int intVal;
	public float floatVal;
	public String stringVal, boolVal;
	
	public Symbol( String identifier ) {
		this.identifier = identifier;
	}
	
	public void assignInt( String value ) {
		intVal = Integer.parseInt(value);
	}
	
	public void assignFloat( String value )  {
		floatVal = Float.parseFloat(value);
	}
	
	public void assignString(String value) {
		stringVal = value.substring(1,value.length()-1);
	}
	
	public void assignBool( String value ) {
		boolVal = value;
	}
}

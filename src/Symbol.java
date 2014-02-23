import java.util.ArrayList;


public class Symbol {
	public String identifier = "";
	public int intVal = 0;
	public float floatVal = 0;
	public String stringVal = "", boolVal = "female";
	public ArrayList<Element> arrayVal = new ArrayList<Element>();
	
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
		//default value is female (equivalent to false)
	}
}

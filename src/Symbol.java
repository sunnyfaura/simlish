import java.util.ArrayList;


public class Symbol {
	public String dataType = "";
	public String identifier = "";
	public int intVal = 0;
	public float floatVal = 0;
	public String stringVal = "", boolVal = "female";
	public ArrayList<Element> arrayVal = new ArrayList<Element>();
	
	public String getIdentifier() {
		return identifier;
	}

	public int getIntVal() {
		return intVal;
	}

	public float getFloatVal() {
		return floatVal;
	}

	public String getStringVal() {
		return stringVal;
	}

	public String getBoolVal() {
		return boolVal;
	}

	public ArrayList<Element> getArrayVal() {
		return arrayVal;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Symbol( String identifier, String dataType ) {
		this.identifier = identifier;
		this.dataType = dataType;
	}
	
	public void assignInt( String value ) {
		intVal = Integer.parseInt(value);
	}
	
	public void assignFloat( String value )  {
		floatVal = Float.parseFloat(value);
	}
	
	public void assignString( String value ) {
		stringVal = value;
	}
	
	public void assignBool( String value ) {
		boolVal = value;
		//default value is female (equivalent to false)
	}
	
	public String getDataType() {
		return dataType;
	}
}

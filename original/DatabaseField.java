//This is the Java class to represent a database field. A database field represents the
//metadata (type, size and so on) for a field in a database table.

public class DatabaseField
{
	//protected because the WebformField class extends from this class
	protected String fieldname;
	protected int type;
	protected int precision;
	protected boolean required;
	
	//constructor methods
	public DatabaseField(String newFieldname, int type, int precision, boolean req)
	{
		fieldname = newFieldname;
		this.type = type;
		this.precision = precision;
		required = req;
	}
	
	public DatabaseField(String newFieldname, int type, int precision)
	{
		fieldname = newFieldname;
		this.type = type;
		this.precision = precision;
	}
	
	public DatabaseField(String newFieldname, int type)
	{
		fieldname = newFieldname;
		type = type;
	}
	
	public DatabaseField(String newFieldname)
	{
		fieldname = newFieldname;
	}
	
	//set and get methods
	public void setFieldname(String newFieldname)
	{
		fieldname = newFieldname;
	}
	
	public String getFieldname()
	{
		return fieldname;
	}
	
	//this is needed for display purposes when we add this DatabaseField to a JList
	public String toString()
	{
		return fieldname;
	}
	
	public void setType(int newType)
	{
		type = newType;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setPrecision(int newPrecision)
	{
		precision = newPrecision;
	}
	
	public int getPrecision()
	{
		return precision;
	}
	
	public boolean isRequired()
	{
		return required;
	}
	
	public void setRequired(boolean newState)
	{
		required = newState;
	}
}
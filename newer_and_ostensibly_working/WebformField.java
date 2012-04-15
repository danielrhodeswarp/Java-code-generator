//This is the Java class to represent a webform field. A webform field is a database field
//(in terms of the metadata) but with a user label for the webform.

public class WebformField extends DatabaseField
{
	private String label;
	
	//constructor methods
	public WebformField(String newFieldname, int type, int precision, boolean required, String label)
	{
		super(newFieldname, type, precision, required);
		
		this.label = label;
	}
	
	public WebformField(String newFieldname, int type, int precision, boolean required)
	{
		super(newFieldname, type, precision, required);
	}
	
	public WebformField(String newFieldname, int type, int precision)
	{
		super(newFieldname, type, precision);
	}
	
	public WebformField(String newFieldname, int type)
	{
		super(newFieldname, type);
	}
	
	public WebformField(String newFieldname)
	{
		super(newFieldname);
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
	
	public void setLabel(String newLabel)
	{
		label = newLabel;
	}
	
	public String getLabel()
	{
		return label;
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
}
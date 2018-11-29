package regex;

public class Regex 
{    
	private String regex = "";
    
    public Regex(String separator)
    {
        this.regex = separator;
    }
    
    public String [] splitVectorString(String content)
    {
        String [] vectorOfStrings = content.split("\\.|\\;|\\s+|\\s*,\\s*|\\s*=>\\s*");
                
        return vectorOfStrings;
    }
}

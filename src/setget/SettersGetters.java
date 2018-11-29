package setget;

public class SettersGetters 
{
    private String key;
    private int value;
    
    public SettersGetters(String newKey, int newValue)
    {
        this.key = newKey;
        this.value = newValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String newKey)
    {
        this.key = newKey;
    }

    public int getValue() 
    {
        return value;
    }
    
    public void setValue(int newValue) 
    {
        this.value = newValue;
    } 
}

package dictionary;

import files.ReadFile;
import regex.Regex;

import java.util.HashMap;
import java.util.Set;

public class Dictionary
{
    private HashMap<String, Integer> dict;
    private int count; // number of files
    
    public Dictionary()
    {
        this.dict = new HashMap();
        this.count = 0; 
    }

    public void createDictionary(String folder, String[] files, String regex)
    {
        for (String file : files) 
        {            
            ReadFile.openFile(folder, file); 
            
            String vectorString = ReadFile.readFileContent();
            
            String[] vectorRegex = new Regex(regex).splitVectorString(vectorString);
            
            String aux = null;
            
            for (String vr : vectorRegex)
            {            	
                aux = vr.trim().toLowerCase();
                
                if (dict.containsKey(aux) == false)
                {
                    dict.put(aux, count++);
                }
            }
        }
    }
    
    public void showDictionary()
    {
        Set<String> keys = this.dict.keySet();
        
        System.out.println("LIST OF WORDS OF THE DICTIONARY\n");
        System.out.println("{word -> position}");
        
        for (String key : keys)
        {
        	System.out.println("  {"+key + " -> " + this.dict.get(key)+"}");
        }
    }
    
    public boolean belongs(String word)
    {
        return this.dict.containsKey(word);
    }
    
    public int position(String word)
    {    
        Integer position = this.dict.get(word);
        
        if (position == null)
        {
        	position = -1;
        }
      
        return position;
    }    

    public int getCount() // number of files
    {
        return count;
    }
        
   public Set<String> getWords()
   {
       return this.dict.keySet();
   }
}

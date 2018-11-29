package dictionary;

import setget.SettersGetters;

import java.util.HashMap;
import java.util.Set;

public class Data 
{
    public static HashMap<String, SettersGetters> frequency(String[] vectorString) 
    {
        HashMap<String, SettersGetters> freqMap = new HashMap();
        
        SettersGetters aux = null;

        for (String vs : vectorString) 
        {
            aux = freqMap.get(vs.trim());
            
            if (aux == null) 
            {
                freqMap.put(vs.trim(), new SettersGetters(vs.trim(), 1));
            } 
            
            else 
            {
                aux.setValue(aux.getValue() + 1);
            }
        }
        
        return freqMap;
    }

    public static void showFrequencyMap(HashMap<String, SettersGetters> freqMap)
    { 
        Set<String> setStrings = freqMap.keySet();

        for (String x : setStrings)
        {
            System.out.println(x + " : " + freqMap.get(x).getValue());
        }
    }
    
    public static double normEuclid(double[] vector)
    {
    	double vectorNorm = 0.0;
    	
    	for (int i = 0; i < vector.length; i++)
    	{
    		vectorNorm += Math.pow(vector[i], 2);
    	}
    	
    	return Math.sqrt(vectorNorm);
    		
    }    
    
   public static double similarity(double[] tfidfRows, double[] ctfidf) 
   {   
	   double similarity = 0.0, innerProduct = 0.0;
       
       for (int i = 0; i < tfidfRows.length; i++) 
       {
           innerProduct += tfidfRows[i] * ctfidf[i];

       }        
       
       similarity += innerProduct / (normEuclid(tfidfRows) * normEuclid(ctfidf));       
       
       return Math.round(similarity * 100.0) / 100.0;        
   }
}

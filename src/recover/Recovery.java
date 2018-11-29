package recover;

import dictionary.*;
import regex.Regex;
import setget.SettersGetters;

import java.util.HashMap;

public class Recovery 
{
    private static String regex;
    private static Dictionary dictionary;
    private static double[] idfVector;

    public Recovery(String rgx, Dictionary dict, double[] idf)
    {
        this.regex = rgx;
        this.dictionary = dict;
        this.idfVector = idf;
    }
    
    // QUERY Vector (double)

    public double[] tfidfQuery(String consulta) 
    {
        String[] vectorString = new Regex(regex).splitVectorString(consulta.toLowerCase());

        HashMap<String, SettersGetters> freqMap = Data.frequency(vectorString);

        Integer[] termFreq = newLine(freqMap);

        double[] queryVector = createTermFrequencyInverseDocFrequency(termFreq);

        return queryVector;
    }

    public HashMap<String, SettersGetters> recover(String query)
    {
        return null;
    }

    public static Integer[] newLine(HashMap<String, SettersGetters> map) 
    {
        Integer[] dict = new Integer[dictionary.getCount()];
        
        for (int i = 0; i < dict.length; i++) 
        {
            dict[i] = 0;
        }

        for (SettersGetters sg : map.values()) 
        {
            int position = dictionary.position(sg.getKey());
            
            dict[position] = sg.getValue();
        }
        
        return dict;
    }

    public double[] createTermFrequencyInverseDocFrequency(Integer[] tf)
    {
        double[] vector = new double[tf.length];

        for (int i = 0; i < tf.length; i++) 
        {
            vector[i] = tf[i] * idfVector[i];
        }
        
        return vector;
    }    

    public void printVector(double[] vector)
    {
    	System.out.println();
        
        for (String eachWord : dictionary.getWords())
        {
        	System.out.print("    (t = " + eachWord + ") \t");
        }
        
        System.out.println();
        
        for (double values : vector)
        {
        	System.out.printf("      %1.2f\t", values);
        }
    }
}

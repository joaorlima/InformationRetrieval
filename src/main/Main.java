package main;

import dictionary.*;
import index.Indexer;
import recover.Recovery;

import java.util.Arrays;

public class Main
{   	
	public static void main(String[] args) 
    {
        String firstDelimiter = new String(new char[11]).replace("\0", "----"); // delimiter
        String secondDelimiter = new String(new char[25]).replace("\0", "----"); // delimiter
    	
    	String [] listOfFiles = {"doc6.txt","doc2.txt","doc3.txt", "doc4.txt", "doc5.txt", "doc1.txt"};
        
        String folder = "//home//joao//eclipse-workspace//TDE//src//documents//";
        String regex = "";
        
        Dictionary dictionary = new Dictionary();
        
        // LIST OF WORDS AND THEIR POSITION
        
        dictionary.createDictionary(folder, listOfFiles, regex);
        dictionary.showDictionary();
        
        System.out.println("\n" + firstDelimiter);
              
        Indexer indexer = new Indexer(dictionary);
        indexer.index(folder, listOfFiles, regex);  
     
        // TF Matrix (integer)
        
        int[][] termFreqMatrix = indexer.getTF(); 
        
        System.out.println("Term-Frequency (TF-matrix)\n");
        indexer.printMatrix(termFreqMatrix);
        
        System.out.println("\n\nNumber of Documentos: " + dictionary.getCount());
        
        System.out.println("\n" + firstDelimiter);
        
        // DF Vector (double)
        
        double[] docFreqVector = indexer.getDF();
        System.out.print("Document Frequency Vector (DF-vector)\n");
        indexer.printVector(docFreqVector);
        
        System.out.println("\n\n" + secondDelimiter);
        
        // IDF Vector (double)
        
        double[] inverseDocFreqVector = indexer.getIDF(); 
        System.out.print("Inverse Document-Frequency (IDF-vector)\n");
        indexer.printVector(inverseDocFreqVector);
        
        System.out.println("\n\n" + secondDelimiter);
        
        // TF-IDF Matrix (double)
        
        double[][] termFreqInverseDocFreqMatrix = indexer.getTFIDF();
        System.out.print("Term-Frequency Inverse Document-Frequency (TF-IDF-matrix)\n");
        indexer.printMatrix(termFreqInverseDocFreqMatrix);
        
        System.out.println("\n\n" + secondDelimiter);
        
        // QUERY
        
        String query = "bb bb"; // it works with the same arguments as the regex
        
        Recovery r = new Recovery(regex, dictionary, inverseDocFreqVector);
        
        // QUERY Vector (double)
        
        double [] queryVector = r.tfidfQuery(query);
        System.out.printf("TFIDF(query = {aa cc})\n");
        r.printVector(queryVector);   
       
        System.out.println("\n\n" + secondDelimiter);
        
        System.out.println("RANKING (in %)");        
                    
        String[][] testString = new String[termFreqInverseDocFreqMatrix.length][1];        
               
        for (int row = 0; row < testString.length; row++)
        {                	        	
        	System.out.println();
    	   
    	    for (int col = 0; col < testString[row].length; col++)
    	    {    		       		   
    	    	testString[0][col] = "Document ";
    		   
    	    	System.out.print(testString[0][col] + (row + 1) + "\t");    		   
    		   
    	    	testString[1][col] = String.valueOf(Data.similarity(termFreqInverseDocFreqMatrix[row], queryVector));
    		  
    	    	System.out.print(testString[1][col] + "\t");      	    	
    	    }
    	   
    	    System.out.println();  	  
        } 
        
        System.out.println("\n" + secondDelimiter);
    }
}

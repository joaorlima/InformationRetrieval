package files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadFile
{
    private static String path, file;
    private static BufferedReader reader;
 
	public static void openFile(String pathFile, String nameFile) 
    {
    	path = pathFile;
        file = nameFile;
        
        openFileContent();
    }    
    
    private static void openFileContent()
    {
        try
        {
            InputStreamReader streamFile;
            FileInputStream entryFile;
            
            entryFile = new FileInputStream(path + file);
            streamFile = new InputStreamReader(entryFile);
            
            reader = new BufferedReader(streamFile);
        } 
        
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
    }

     public static String readLine()
     {

        String line = null;
        
        try
        {
        	line = reader.readLine();
        } 
        
        catch (Exception e)
        {
            System.out.println("readLine: " + e);
        }

        return line;
    }

    public static String readFileContent() 
    {
        String text = "";
        String line = "";
        
        try
        {
            while ((line = readLine()) != null)
            {
                text += line + "\n";
            }            
        } 
        
        catch (Exception e)
        {
            System.out.println("readContent: " + e);
        }
        
        return text;
    }
}

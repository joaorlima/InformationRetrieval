package index;

import dictionary.Dictionary;
import dictionary.Data;
import files.ReadFile;
import regex.Regex;
import setget.SettersGetters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.Math.log10;

public class Indexer
{
	private Dictionary dictionary;
	private int[][] TF;
	private double[] DF, IDF;
	private double[][] TFIDF;

	public Indexer(Dictionary dico)
	{
		this.dictionary = dico;
	}

	public void index(String folder, String[] files, String regex) 
	{
		HashMap<String, SettersGetters> freqMap;

		ArrayList<Integer[]> matrix = new ArrayList();

		for (String file : files) 
		{
			ReadFile.openFile(folder, file);

			String vectorString = ReadFile.readFileContent().toLowerCase();

			String[] vectorRegex = new Regex(regex).splitVectorString(vectorString);

			freqMap = Data.frequency(vectorRegex);

			matrix.add(newLine(freqMap));
		}

		TF = createTermFrequency(matrix);

		DF = createDocFrequency(TF);

		IDF = createInverseDocFrequency(DF, files.length);

		TFIDF = createTermFrequencyInverseDocFrequency(TF, IDF);
	}

	public int[][] getTF()
	{
		return TF;
	}

	public double[] getDF()
	{
		return DF;
	}

	public double[] getIDF()
	{
		return IDF;
	}

	public double[][] getTFIDF() 
	{
		return TFIDF;
	}

	public Integer[] newLine(HashMap<String, SettersGetters> map)
	{
		Integer[] mapLength = new Integer[dictionary.getCount()];

		for (int i = 0; i < mapLength.length; i++)
		{
			mapLength[i] = 0;
		}

		for (SettersGetters p : map.values()) 
		{
			int position = dictionary.position(p.getKey());

			mapLength[position] = p.getValue();
		}

		return mapLength;
	}

	public void printMatrix(ArrayList<Integer[]> intMatrix)
	{
		for (Integer[] values : intMatrix) 
		{
			System.out.println();

			for (Integer i : values)
			{
				System.out.print(i + "\t");
			}
		}
	}
	
	 // TF Matrix (integer)

	public int[][] createTermFrequency(ArrayList<Integer[]> matrix)
	{
		int row = matrix.size();
		int col = dictionary.getCount();

		int[][] intMatrix = new int[row][col]; 

		int i = -1, j = -1;

		for (Integer[] intVector : matrix) 
		{
			i++;
			j = -1;

			for (Integer values : intVector)
			{
				j++;
				intMatrix[i][j] = values;
			}
		}

		return intMatrix;
	}

	public void printMatrix(int[][] matrix)
	{
		for (String eachWord : dictionary.getWords())
		{
			System.out.print("    (t = " + eachWord + ") \t");
		}

		for (int row = 0; row < matrix.length; row++)
		{
			System.out.println();

			for (int col = 0; col < matrix[0].length; col++)
			{
				System.out.printf("       %1.2s\t", matrix[row][col]);
			}
		}
	}

	public void printMatrix(double[][] matrix) 
	{
		System.out.println();

		for (String eachWord : dictionary.getWords()) 
		{
			System.out.print("    (t = " + eachWord + ") \t");
		}

		for (int row = 0; row < matrix.length; row++)
		{
			System.out.println();

			for (int j = 0; j < matrix[0].length; j++) 
			{
				System.out.printf("      %1.2f\t", matrix[row][j]);
			}
		}
	}
	
	// DF Vector (double)

	public double[] createDocFrequency(int[][] matrix) 
	{
		double[] doubleMatrix = new double[dictionary.getCount()]; 

		for (int i = 0; i < doubleMatrix.length; i++) 
		{
			doubleMatrix[i] = 0.0;
		}

		for (int row = 0; row < matrix.length; row++)
		{
			for (int col = 0; col < matrix[0].length; col++)
			{
				if (matrix[row][col] != 0) 
				{
					doubleMatrix[col]++;
				}
			}
		}

		return doubleMatrix;
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
	
	  // IDF Vector (double)

	public double[] createInverseDocFrequency(double[] vector, int num) 
	{
		double[] doubleVector = new double[vector.length]; 

		for (int i = 0; i < vector.length; i++) 
		{
			doubleVector[i] = log10(num / vector[i]) / log10(2); // log formula
		}

		return doubleVector;
	}
	
	// TF-IDF Matrix (double)

	public double[][] createTermFrequencyInverseDocFrequency(int[][] tfMatrix, double[] idfVector)
	{
		int row = tfMatrix.length;
		int col = tfMatrix[0].length;

		double[][] doubleMatrix = new double[row][col]; // TF-IDF

		for (int rowMult = 0; rowMult < row; rowMult++)
		{
			for (int colMult = 0; colMult < col; colMult++)
			{
				doubleMatrix[rowMult][colMult] = tfMatrix[rowMult][colMult] * idfVector[colMult];
			}
		}

		return doubleMatrix;
	}
}

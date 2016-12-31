package randomizeSorting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class RandomNumberSorting
{
	
	private static ArrayList<Integer> numberArray = new ArrayList<Integer>();
	private static ArrayList<Integer> dataSize = new ArrayList<Integer>();
	static Sorter sorter;

	public static void main(String[] args)
	{
		readDataSizeFromFile("dataFile");
		sorter = new Sorter(numberArray, dataSize) ;
		sorter.performSorting();
	}
	
	/**
	 * Read Data from file
	 * @param fileName
	 */
	private static void readDataSizeFromFile(String fileName)
	{
		FileReader dataFile;
		BufferedReader reader = null;
		try
		{
			dataFile = new FileReader(new File("src/data/" + fileName + ".txt"));
			reader = new BufferedReader(dataFile);
			String line;
			while ((line = reader.readLine()) != null)
			{
				String[] data = line.split(" ");
				dataSize.add(Integer.parseInt(data[1]));
				System.out.println(data[1]);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		} finally
		{
			try
			{
				reader.close();
			} catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}

	}

}

// Data3 30000
// Data4 40000
// Data5 50000
// Data6 60000
// Data7 70000
// Data8 80000
// Data9 90000
// Data10 100000

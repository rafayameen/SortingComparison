package randomizeSorting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Sorter
{
	private ArrayList<Integer> dataSize = new ArrayList<Integer>();
	private ArrayList<Integer> randomArray = new ArrayList<Integer>();
	private ArrayList<Integer> duration = new ArrayList<Integer>();

	private long startTime;
	private long endTime;

	public Sorter(ArrayList<Integer> numberArray, ArrayList<Integer> dataSize)
	{
		this.randomArray = numberArray;
		this.dataSize = dataSize;
		startTime = endTime = 0;
	}

	/**
	 * All the logic of program which includes reading size of numbers to be
	 * sorted from dataFile Universal Unique ID's based on size sorting the
	 * numbers passing and writing numbers and other data to specific data file
	 */
	public void performSorting()
	{
		String[] timeTaken = new String[dataSize.size()];
		for (int i = 0; i < dataSize.size(); i++)
		{

			generateRandomNumber(dataSize.get(i));

			startTime = endTime = 0;
			startTime = System.nanoTime();
			sortUsingBubbleSort(randomArray, randomArray.size());
			endTime = System.nanoTime();
			timeTaken[0] = String.valueOf((endTime - startTime)/1000000000L);
			 System.out.println("Time taken for " + randomArray.size() + " numbers");
			 System.out.println("Bubble Sort: " + timeTaken[0]);
			 
			 
			startTime = endTime = 0;
			startTime = System.nanoTime();
			sortUsingMergeSort(randomArray, randomArray.size());
			endTime = System.nanoTime();
			timeTaken[1] = String.valueOf((endTime - startTime)/1000000000L);
			 System.out.println("Merge Sort: " + timeTaken[1]);
			Integer fileNum = i + 1;
			String fileName = "Data" + fileNum.toString();
			writeDataToFile(fileName, randomArray, duration, timeTaken);
		}

	}

	/**
	 * Sort Array Using Merge Sort
	 * 
	 * @param numberArray2
	 * @param size
	 * @return
	 */
	private void sortUsingMergeSort(ArrayList<Integer> arr, int N)
	{

		int L = 1; // size of sub array
		int[] A = new int[arr.size()];
		for (int i = 0; i < A.length; i++)
			A[i] = arr.get(i);

		int[] B = new int[A.length];
		while (L < N)
		{
			mergePass(A, N, L, B);
			mergePass(B, N, 2 * L, A);
			L *= 4;
		}
	}


	private void mergePass(int[] A, int N, int L, int[] B)
	{
		int Q = N / (2 * L);
		int S = 2 * L * Q;
		int R = N - S;

		int LB;
		int i;
		for (i = 0; i < Q; i++)
		{
			LB = (2 * i) * L;
			merge(A, L, LB, A, L, LB + L, B, LB);

		}

		if (R <= L)
		{
			for (i = 0; i < R; i++)
				B[S + i] = A[S + i];
		} else
			merge(A, L, S, A, R - L, S + L, B, S);

	}

	private void merge(int[] A, int n1, int index1, int[] B, int n2, int index2, int[] C, int index)
	{

		cleanUp();
		while ((n1 > 0) && (n2 > 0))
		{

			if (A[index1] < B[index2])
			{
				C[index] = A[index1];
				index++;
				index1++;
				n1--;
			} else
			{
				C[index] = B[index2];
				index++;
				index2++;
				n2--;
			}
		}

		while (n1 > 0)
		{
			C[index] = A[index1];
			index++;
			index1++;
			n1--;
		}

		while (n2 > 0)
		{
			C[index] = B[index2];
			index++;
			index2++;
			n2--;
		}

		for (int i = 0; i < C.length; i++)
			randomArray.add(C[i]);
	}

	/**
	 * Clean the numbers ArrayList after every iteration of sorting
	 */
	private void cleanUp()
	{
		// dataSize.clear();
		randomArray.clear();
	}

	/**
	 * Bubble Sort the passed ArrayList of Integers
	 * 
	 * @param array
	 * @param size
	 * @return
	 */
	private void sortUsingBubbleSort(ArrayList<Integer> array, int size)
	{

		int temp = 0;
		for (int round = 1; round <= size - 1; round++)
		{
			for (int i = 0; i <= size - 1 - round; i++)
			{
				if (array.get(i) > array.get(i + 1))
				{
					temp = array.get(i + 1);
					array.set(i + 1, array.get(i));
					array.set(i, temp);
				}
			}
		}
	}

	/**
	 * Generate Random number based on size from dataFile
	 * 
	 * @param size
	 */
	private void generateRandomNumber(Integer size)
	{
		randomArray.clear();

		for (int j = 0; j < size; j++)
		{
			randomArray.add((int) UUID.randomUUID().getMostSignificantBits());
		}
	}


	/**
	 * Write Data To File
	 * 
	 * @param fileName
	 * @param numberArray2
	 * @param duration.get(index)
	 * @param type
	 */
	private void writeDataToFile(String fileName, ArrayList<Integer> numberArray2, ArrayList<Integer> duration,
			String[] type)
	{
		FileWriter fwr = null;
		try
		{
			fwr = new FileWriter(new File("src/data/" + fileName + ".txt"));

			for (int i = 0; i < numberArray2.size(); i++)
			{
				fwr.append(Integer.toString(numberArray2.get(i)));
				fwr.append("\n");

			}

			fwr.append("\nNumbers Sorted: " + randomArray.size());
			fwr.append("\n\nTime Taken: \n");
			for (int i = 0; i < 2; i++)
			{
				if (i == 0)
					fwr.append("Bubble Sort:" + type[i] + "\n");
				else
					fwr.append("Merge Sort:" + type[i] + "\n");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				this.duration.clear();
				cleanUp();
				randomArray.trimToSize();
				fwr.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

}

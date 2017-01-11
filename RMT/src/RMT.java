/* RMT.java

 * 
 *   Created on: Jan 21, 2016
 *        Author: Ofir Etz-Hadar.
 */

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;



public class RMT {

	static String usage = "Command: build - Build a filter from a FASTA reference file\n"
			+ "         query - Query a filter given a FASTQ/FASTA file. Large files and compressed files supported\n"
			+ "         remove - Remove (contamination) sequences from FASTA file\n"
			+ "         info - Print information about a the filter";
	
	public static void main(String[] args){
		

	
		
		long startTime;
		long stopTime;
		long elapsedTime = 0;
		
		double trues = 0;
		double falses = 0;
		Boolean hit = false;
		String[] arr = new String[3];
		
		// START counting 2 or more errors
		
		FastaSequence f = new FastaSequence("NC21_old-reads.fa");
		String[] sequence = f.getSequence();
		String[] description = f.getDescription1();
		String[] seq2 = new String[15317];
		String[] des2 = new String[15317];
		int j = 0;
		
		double number_of_potential_falseNegatives = 0;
		double number_of_reads = 0;
		double number_of_Nreads = 0;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("r.fa", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < description.length; i++){
			if(!sequence[i].contains("N")){
				des2[j] = description[i];
				seq2[j] = sequence[i];
				j++;
			if(description[i].contains("A,") || description[i].contains("C,") || description[i].contains("G,") || description[i].contains("T,") || description[i].contains("-,")){
				number_of_potential_falseNegatives++;
			}
			number_of_reads++;
			}
			else{
				number_of_Nreads++;
			}
			
		}
		for(int i = 0; i < des2.length; i++){
			writer.println(des2[i]);
			writer.println(seq2[i]);
		}
		writer.close();
		
		System.out.println("Number of potential false negatives: " + number_of_potential_falseNegatives);
		System.out.println("Total number of reads: " + number_of_reads);
		System.out.println("Rate of potential false negatives: " + (number_of_potential_falseNegatives/number_of_reads));
		System.out.println("Sensitivity: " + ((number_of_reads-number_of_potential_falseNegatives)/number_of_reads));
		System.out.println("Nuber of reads contains N: " + number_of_Nreads);
		
	}
}

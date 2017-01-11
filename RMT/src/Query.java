import java.util.ArrayList;

/* Query.java
 * 
 *   Created on: Jan 21, 2016
 *        Author: Ofir Etz-Hadar.
 */

public class Query {
	
	private int word_size;
	private int bvs_size;
	private int L;
	private int k;
	private String query_file_name;
	private ArrayList<Pair> hamming_pair_list;
	private ArrayList<Pair> plus1A_pair_list;
	private ArrayList<Pair> plus1C_pair_list;
	private ArrayList<Pair> plus1G_pair_list;
	private ArrayList<Pair> plus1T_pair_list;
	private ArrayList<Pair> minus1_pair_list;

	
	public Query(){}
	
	public Query(String q, int word_size, int L, int bvs_size, int k){// args = query <filter_name> <query_name>
		this.word_size = word_size;
		this.L = L;
		this.bvs_size = bvs_size;
		this.k = k;
		this.hamming_pair_list = new ArrayList<Pair>();
		this.plus1A_pair_list = new ArrayList<Pair>();
		this.plus1C_pair_list = new ArrayList<Pair>();
		this.plus1G_pair_list = new ArrayList<Pair>();
		this.plus1T_pair_list = new ArrayList<Pair>();

		this.minus1_pair_list = new ArrayList<Pair>();

		String sequence = q;

			toHammingPairs(hamming_pair_list, sequence);
			toMinusPairs(minus1_pair_list, sequence);

			toPlusXPairs(plus1A_pair_list, sequence, "A");
			toPlusXPairs(plus1C_pair_list, sequence, "C");
			toPlusXPairs(plus1G_pair_list, sequence, "G");
			toPlusXPairs(plus1T_pair_list, sequence, "T");
				
	}
	
	// generate hamming type pairs
	public void toHammingPairs(ArrayList<Pair> list, String s){
		for(int i = 0; i < this.word_size; i = i+L){
			list.add(new Pair(s.substring(0, i).concat(s.substring(i+L, s.length())), s.substring(i, i+L) ,this.bvs_size, this.k));
		}	
	}
	
	// generate plus1 type pairs
	public void toPlusXPairs(ArrayList<Pair> list, String s, String x){
		for(int i = 0; i < this.word_size; i = i+L){
			if(i == this.word_size-L){// The case for the last L-mer on a specific m-mer 
				list.add(new Pair(s.substring(0, i), s.substring(i, i+L).concat(x) ,this.bvs_size, this.k));
				
			}else
				list.add(new Pair(s.substring(0, i).concat(s.substring(i+L+1, s.length()).concat(x)), s.substring(i, i+L).concat(s.substring(i+L, i+L+1)) ,bvs_size, k));
		}	
	}
	
	// generate minus1 type pairs
	public void toMinusPairs(ArrayList<Pair> list, String s){
		for(int i = 0; i < this.word_size; i = i+L){
			if(i == this.word_size-L){// The case for the last L-mer on a specific m-mer 
				list.add(new Pair(s.substring(0, i+1), s.substring(i+1, i+L) ,this.bvs_size, this.k));
				
			}else
				list.add(new Pair(s.substring(0, i).concat(s.substring(i+L-1, s.length())), s.substring(i, i+L-1) ,this.bvs_size, this.k));
		}	
	}
	
	public ArrayList<Pair> getHammingPairList(){
		return this.hamming_pair_list;
	}
	
	public ArrayList<Pair> getPlus1APairList(){
		return this.plus1A_pair_list;
	}

	public ArrayList<Pair> getPlus1CPairList(){
		return this.plus1C_pair_list;
	}
	
	public ArrayList<Pair> getPlus1GPairList(){
		return this.plus1G_pair_list;
	}
	
	public ArrayList<Pair> getPlus1TPairList(){
		return this.plus1T_pair_list;
	}
	
	public ArrayList<Pair> getMinus1PairList(){
		return this.minus1_pair_list;
	}
	
	public int getWordSize(){
		return this.word_size;
	}
	
	public void setWordSize(int size){
		this.word_size = size;
	}
	
	// get the size of the encoded word
	public int getBvsSize(){
		return this.bvs_size;
	}
	
	public void setBvsSize(int size){
		this.bvs_size = size;
	}
	
	public String getQueryFileName(){
		return this.query_file_name;
	}

	// TO DO: Query for an element
	public Boolean query(Query query){
		
		return true;
	}
	

}

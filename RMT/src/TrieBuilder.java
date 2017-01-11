import java.io.Serializable;
import java.util.ArrayList;

/*TrieBuilder.java
 * 
 *   Created on: Jan 21, 2016
 *        Author: Ofir Etz-Hadar.
 */

public class TrieBuilder implements Serializable{
	
	private int word_size; // m-mer size
	private int bvs_size;
	private int L;
	private int k; // Number of hash functions
	private String file_name;
	private String ref_name;
	public ArrayList<Trie<Trie<String>>> mills; // Millipede trees
	public ArrayList<Trie<Trie<String>>> plus1mills; 
	public ArrayList<Trie<Trie<String>>> minus1mills; 


	

	public TrieBuilder(){}
	
	public TrieBuilder(String ref, String file, int word, int L, int bvs){
		this.setRefName(ref);
		this.setFileName(file);
		this.setWordSize(word);
		this.setBvsSize(bvs);
		this.setL(L);
		this.k = 7; 
		
		mills = new ArrayList<Trie<Trie<String>>>();
		plus1mills = new ArrayList<Trie<Trie<String>>>();
		minus1mills = new ArrayList<Trie<Trie<String>>>();

		this.build(file); 
	}
	
	// build <ref_name> <file_name> <word_size> L <bvs_size> k (k is optional argument)
	public TrieBuilder(String[] args){
		this.ref_name = (args[1]);
		this.file_name = (args[2]);
		this.word_size = (Integer.parseInt(args[3]));
		this.bvs_size = (Integer.parseInt(args[5]));
		this.L = (Integer.parseInt(args[4]));
		
		if(args.length > 6)
			this.k = (Integer.parseInt(args[6])); 
		else
			this.k = computeOptimalHashes();
		
		mills = new ArrayList<Trie<Trie<String>>>();
		plus1mills = new ArrayList<Trie<Trie<String>>>();
		minus1mills = new ArrayList<Trie<Trie<String>>>();

		
		this.build(args[1]); 
		
	}
	
	public int computeOptimalHashes(){
		FastaSequence f = new FastaSequence(this.ref_name);
		String sequence = f.getSequence()[0];
		
		return (int) (Math.log(2)*((double)(this.bvs_size)/((double)(sequence.length()-this.word_size))));
	}
	
	public void build(String file_name){
		ArrayList<Pair> list = parseFasta(file_name);
		
		for(int i = 0; i < (getWordSize()/getL()); i++){
			mills.add(new Trie<Trie<String>>());
			plus1mills.add(new Trie<Trie<String>>());
			minus1mills.add(new Trie<Trie<String>>());

		}
		
		createHammingTries(list);
		createPlusTries(list);
		
		list.clear();
		list = parseFasta2(file_name);
		createMinusTries(list);
		
	}
	
	// Parser for the plus1 tries and the hamming tries
	public ArrayList<Pair> parseFasta(String file_name){
		ArrayList<Pair> list = new ArrayList<Pair>();

		FastaSequence f = new FastaSequence(file_name);
		String sequence = f.getSequence()[0];
		
		for(int i = 0; i < sequence.length()-this.word_size+1; i++){
			toPairs(list, sequence.substring(i, i+this.word_size));
		}
		
		return list;
		
	}
	
	// Parser for the minus1 tries
	public ArrayList<Pair> parseFasta2(String file_name){
		ArrayList<Pair> list = new ArrayList<Pair>();

		FastaSequence f = new FastaSequence(file_name);
		String sequence = f.getSequence()[0];

		for(int i = 0; i < sequence.length()-this.word_size; i++){
			toPairs2(list, sequence.substring(i, i+this.word_size+1));
		}
		
		return list;
		
	}
	// list to type 1 pair
	public void toPairs(ArrayList<Pair> list, String s){
		for(int i = 0; i < this.word_size; i = i+L){
			list.add(new Pair(s.substring(0, i).concat(s.substring(i+L, s.length())), s.substring(i, i+L) ,bvs_size, k));
		}	
	}
	// list to type 2 pair
	public void toPairs2(ArrayList<Pair> list, String s){
		for(int i = 0; i < this.word_size; i = i+L){
			if(i == this.word_size-L){// The case for the last L-mer on a specific m-mer 
				list.add(new Pair(s.substring(0, i+1), s.substring(i+1, i+L+1), s.substring(0, i).concat(s.substring(i+1,i+2)), s.substring(i+2, i+L+1),bvs_size, k, true));
			}else
				list.add(new Pair(s.substring(0, i).concat(s.substring(i+L, s.length())), s.substring(i, i+L) ,bvs_size, k));

		}	
	}
	
	// create the hamming tree for a given pair list
	public void createHammingTries(ArrayList<Pair> list){
		int div = getWordSize()/getL();
		int treeIndex = -1;
		for(int i = 0; i < div; i++){
			treeIndex++;
			for(int j = 0; j < list.size()/div; j++){
				mills.get(treeIndex).insertHammingPair(list.get(j*div+i));
					
			}
			
		}
	}
	
	// create the plus tree  for a given pair list
	public void createPlusTries(ArrayList<Pair> list){
		int div = getWordSize()/getL();
		int treeIndex = -1;
		for(int i = 0; i < div; i++){
			treeIndex++;
			for(int j = 0; j < list.size()/div; j++){
				plus1mills.get(treeIndex).insertPlus1Pair(list.get(j*div+i));
					
			}
			
		}
	}
	
	// create the minus tree for a given pair list
	public void createMinusTries(ArrayList<Pair> list){
		int div = getWordSize()/getL();
		int treeIndex = -1;
		for(int i = 0; i < div; i++){
			treeIndex++;
			for(int j = 0; j < list.size()/div; j++){
				minus1mills.get(treeIndex).insertMinus1Pair(list.get(j*div+i));
					
			}
			
		}
	}
	
	// query all trees
	// looks for hit with up to 2 mismaches
	// for more details about the search algorithm ask ofiretz@post.bgu.ac.il
	public Boolean query(Query query){
		ArrayList<Pair> hamming_list = query.getHammingPairList();
		ArrayList<Pair> plusA_list = query.getPlus1APairList();
		ArrayList<Pair> plusC_list = query.getPlus1CPairList();
		ArrayList<Pair> plusG_list = query.getPlus1GPairList();
		ArrayList<Pair> plusT_list = query.getPlus1TPairList();
		ArrayList<Pair> minus1_list = query.getMinus1PairList();
		
		Boolean hit = false;
		
		int div = getWordSize()/getL();
		int treeIndex = -1;
		for(int i = 0; (i < div) && (!hit); i++){
			treeIndex++;
			for(int j = 0; (j < hamming_list.size()/div) && (!hit); j++){
				Pair p = hamming_list.get(j*div+i);
				p.applyBVS();
				if(mills.get(treeIndex).pairSearch(p))
					hit = true;
					
			}
			
		}
		
		treeIndex = -1;
		
		for(int i = 0; (i < div) && (!hit); i++){
			treeIndex++;
			for(int j = 0; (j < plusA_list.size()/div) && (!hit); j++){
				Pair p = plusA_list.get(j*div+i);
				p.applyBVS();
				if(plus1mills.get(treeIndex).pairSearch(p))
					hit = true;			
			}
		}
		
		treeIndex = -1;
		
		for(int i = 0; (i < div) && (!hit); i++){
			treeIndex++;
			for(int j = 0; (j < plusC_list.size()/div) && (!hit); j++){
				Pair p = plusC_list.get(j*div+i);
				p.applyBVS();
				if(plus1mills.get(treeIndex).pairSearch(p))
					hit = true;		
			}
		}
		
		treeIndex = -1;
		
		for(int i = 0; (i < div) && (!hit); i++){
			treeIndex++;
			for(int j = 0; (j < plusG_list.size()/div) && (!hit); j++){
				Pair p = plusG_list.get(j*div+i);
				p.applyBVS();
				if(plus1mills.get(treeIndex).pairSearch(p))
					hit = true;
					
			}
		}
		
		treeIndex = -1;
		
		for(int i = 0; (i < div) && (!hit); i++){
			treeIndex++;
			for(int j = 0; (j < plusT_list.size()/div) && (!hit); j++){
				Pair p = plusT_list.get(j*div+i);
				p.applyBVS();
				if(plus1mills.get(treeIndex).pairSearch(p))
					hit = true;
					
			}
		}
				
		treeIndex = -1;
		
		for(int i = 0; (i < div) && (!hit); i++){
			treeIndex++;
			for(int j = 0; (j < minus1_list.size()/div) && (!hit); j++){
				Pair p = minus1_list.get(j*div+i);
				p.applyBVS();
				if(minus1mills.get(treeIndex).pairSearch(p))
					hit = true;
					
			}	
		}
		return hit;
	}
	
	public int getWordSize(){
		return this.word_size;
	}
	
	public void setWordSize(int size){
		this.word_size = size;
	}
	
	public int getBvsSize(){
		return this.bvs_size;
	}
	
	public void setBvsSize(int size){
		this.bvs_size = size;
	}
	
	public void setL(int l){
		this.L = l;
	}
	
	public int getL(){
		return this.L;
	}
	
	public int getK(){
		return this.k;
	}
	
	public String getFileName(){
		return this.file_name;
	}

	public void setFileName(String name){
		this.file_name = new String(name);
	}
	
	public String getRefName(){
		return this.ref_name;
	}

	public void setRefName(String name){
		this.ref_name = new String(name);
	}

	
}

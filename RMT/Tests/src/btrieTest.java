import java.util.ArrayList;
import java.util.Comparator;

public class btrieTest {

	public static void main(String[] args){
		
		Boolean test = true;
		BTrie<Boolean> trie = new BTrie<Boolean>();
		Comparator lessThan = null;
		ArrayList<String> list = null;

		int[] heap = new int[2];
		heap[0] = 1;
		heap[1] = 3;

		trie.insertString(heap, true,list,4);
		
		if(!(trie.search(heap, 4)))
			test = false;
		
		
		heap[0] = 0;
		heap[1] = 2;

		trie.insertString(heap, true,list,4);
		
		if(!(trie.search(heap, 4)))
			test = false;
		
		heap[0] = 0;
		heap[1] = 1;
		
		if((trie.search(heap, 4)) != null)
			test = false;
		
		if(test){
			System.out.println("Passed!");

		}
		else{
			System.out.println("Failure!");

		}
		
	}
}
